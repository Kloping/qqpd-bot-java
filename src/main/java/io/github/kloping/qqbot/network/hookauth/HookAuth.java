package io.github.kloping.qqbot.network.hookauth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.github.kloping.io.ReadUtils;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.Pack;
import io.github.kloping.qqbot.impl.BaseConnectedEvent;
import io.github.kloping.qqbot.network.AuthAndHeartbeat;
import io.github.kloping.qqbot.network.WssWorker;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.spt.interfaces.Logger;
import lombok.Getter;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static io.github.kloping.qqbot.Resource.GSON;
import static io.github.kloping.spt.PartUtils.getExceptionLine;

/**
 * @author github kloping
 * @date 2025/4/18-10:02
 */
@Entity
public class HookAuth {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @AutoStand
    private Logger logger;

    @AutoStand
    WssWorker wssWorker;

    @AutoStand
    Starter.Config config;

    @Getter
    private HttpServer httpServer;

    public void webhookServerStart() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(config.getWebhookport()), 0);
            // 显式设置线程池 避免默认单线程调度在高并发/慢处理时被占满导致"打不开"
            httpServer.setExecutor(Executors.newFixedThreadPool(
                    Math.max(4, Runtime.getRuntime().availableProcessors() * 2)));
            httpServer.createContext(config.getWebhookpath(), this::handleWebhook);
            // 健康检查端点 便于区分"端口死了"还是"业务path挂了"
            httpServer.createContext("/health", exchange -> writeResponse(exchange, 200, "ok"));
            httpServer.start();
            logger.info(String.format(BaseConnectedEvent.FORMAT_SERVER, config.getAppid()));
        } catch (IOException e) {
            logger.error("在WebHook服务启动时失败\n" + getExceptionLine(e));
        }
    }

    /**
     * webhook 请求处理 保证:
     * <br/>1. 无论成功失败 finally 中都会写响应并 close(exchange) 避免连接/线程泄漏
     * <br/>2. resp 永不为 null 避免 resp.length() NPE
     * <br/>3. body 非法 JSON / pack 为 null / op 为 null 时直接 400 不再 NPE
     */
    private void handleWebhook(HttpExchange exchange) {
        String resp = "{}";
        int status = 200;
        try {
            String body = ReadUtils.readAll(exchange.getRequestBody(), "UTF-8");
            logger.log(String.format("webhook-r: %s", body));
            Pack pack = null;
            try {
                if (body != null && !body.trim().isEmpty()) {
                    pack = GSON.fromJson(body, Pack.class);
                }
            } catch (Exception e) {
                logger.waring("WebHook请求体解析失败: " + getExceptionLine(e));
            }
            if (pack == null || pack.getOp() == null) {
                // 空body / 浏览器直接GET / 非法JSON 直接返回 400 避免后续 NPE
                status = 400;
                resp = "{}";
            } else if (pack.getOp() == 13) {
                resp = auth(body, pack, exchange);
            } else {
                try {
                    List<String> sigs = exchange.getRequestHeaders().get("X-Signature-Ed25519");
                    List<String> ts = exchange.getRequestHeaders().get("X-Signature-Timestamp");
                    if (sigs != null && !sigs.isEmpty() && ts != null && !ts.isEmpty()) {
                        String sig = sigs.get(0);
                        String timestamp = ts.get(0);
                        KeyPair keyPair = getKeyPair();
                        boolean isValid = verifySignature(sig, timestamp, body.getBytes(StandardCharsets.UTF_8), keyPair.getPublic().getEncoded());
                        resp = String.valueOf(isValid);
                    }
                } catch (Exception e) {
                    logger.error("验证签名报错(不影响接收和发送)：\n" + getExceptionLine(e));
                }
                final Pack fpack = pack;
                wssWorker.getOnPackReceives().stream().filter(o -> !(o instanceof AuthAndHeartbeat))
                        .forEach(p -> p.onReceive(fpack));
            }
        } catch (Exception e) {
            logger.error("WebHook服务处理请求异常：\n" + getExceptionLine(e));
            status = 500;
            resp = "{}";
        } finally {
            logger.log("WebHook服务响应: " + resp);
            writeResponse(exchange, status, resp);
        }
    }

    /**
     * 统一写响应 使用 byte[] 长度 并在 finally 中无条件 close(exchange)
     */
    private void writeResponse(HttpExchange exchange, int status, String resp) {
        if (resp == null) resp = "{}";
        try {
            byte[] bytes = resp.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(status, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } catch (Exception e) {
            logger.error("WebHook服务写响应失败：\n" + getExceptionLine(e));
        } finally {
            exchange.close();
        }
    }

    private KeyPair getKeyPair() {
        return generateEd25519KeyPair(prepareSeed(config.getSecret()).getBytes(StandardCharsets.UTF_8));
    }

    private String auth(String body, Pack pack, HttpExchange exchange) {
        try {
            logger.info("验证有效性...");
            KeyPair keyPair = getKeyPair();
            Map<String, String> packD = (Map<String, String>) pack.getD();
            String plain_token = packD.get("plain_token");
            String event_ts = packD.get("event_ts");
            byte[] message = (event_ts + plain_token).getBytes(StandardCharsets.UTF_8);
            byte[] signature = signMessage(keyPair.getPrivate(), message);
            return String.format("{\"plain_token\": \"%s\", \"signature\": \"%s\"}", plain_token, bytesToHex(signature));
        } catch (Exception e) {
            logger.error("验证失败：\n" + getExceptionLine(e));
            return "{}";
        }
    }


    public boolean verifySignature(String signatureHex, String timestamp, byte[] httpBody, byte[] publicKeyBytes) {
        try {
            byte[] sig = Hex.decode(signatureHex);
            if (sig.length != 64 || (sig[63] & 0xE0) != 0) {
                logger.waring("Invalid signature format");
                return false;
            }
            ByteArrayOutputStream msg = new ByteArrayOutputStream();
            msg.write(timestamp.getBytes(StandardCharsets.UTF_8));
            msg.write(httpBody);
            Ed25519Signer verifier = new Ed25519Signer();
            verifier.init(false, new Ed25519PublicKeyParameters(publicKeyBytes, 0));
            verifier.update(msg.toByteArray(), 0, msg.size());
            return verifier.verifySignature(sig);
        } catch (Exception e) {
            logger.error("验证签名报错：\n" + getExceptionLine(e));
            return false;
        }
    }

    private String prepareSeed(String seed) {
        if (seed.length() < 32) seed = repeat(seed, 2);
        return seed.substring(0, 32);
    }

    private KeyPair generateEd25519KeyPair(byte[] seed) {
        Ed25519KeyPairGenerator generator = new Ed25519KeyPairGenerator();
        generator.init(new KeyGenerationParameters(null, 32));
        Ed25519PrivateKeyParameters privateKeyParams = new Ed25519PrivateKeyParameters(seed, 0);
        Ed25519PublicKeyParameters publicKeyParams = privateKeyParams.generatePublicKey();
        byte[] privateKeyBytes = privateKeyParams.getEncoded();
        byte[] publicKeyBytes = publicKeyParams.getEncoded();
        return new KeyPair(new CustomPublicKey(publicKeyBytes), new CustomPrivateKey(privateKeyBytes));
    }

    private byte[] signMessage(PrivateKey privateKey, byte[] message) {
        Ed25519Signer signer = new Ed25519Signer();
        signer.init(true, new Ed25519PrivateKeyParameters(privateKey.getEncoded(), 0));
        signer.update(message, 0, message.length);
        return signer.generateSignature();
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static String repeat(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) sb.append(str);
        return sb.toString();
    }
}
