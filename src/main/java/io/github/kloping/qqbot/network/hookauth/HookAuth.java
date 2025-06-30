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
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.util.List;
import java.util.Map;

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

    public void webhookServerStart() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(config.getWebhookport()), 0);
            server.createContext(config.getWebhookpath(), exchange -> {
                String body = ReadUtils.readAll(exchange.getRequestBody(), "UTF-8");
                logger.log(String.format("webhook-r: %s", body));
                Pack pack = GSON.fromJson(body, Pack.class);
                String resp = null;
                if (pack.getOp() == 13) {
                    resp = auth(body, pack, exchange);
                } else {
                    try {
                        List<String> sigs = exchange.getRequestHeaders().get("X-Signature-Ed25519");
                        List<String> ts = exchange.getRequestHeaders().get("X-Signature-Timestamp");
                        String sig = sigs.get(0);
                        String timestamp = ts.get(0);
                        KeyPair keyPair = getKeyPair();
                        boolean isValid = verifySignature(sig, timestamp, body.getBytes(StandardCharsets.UTF_8), keyPair.getPublic().getEncoded());
                        resp = String.valueOf(isValid);
                    } catch (Exception e) {
                        logger.error("验证签名报错(不影响接收和发送)：\n" + getExceptionLine(e));
                    }
                    wssWorker.getOnPackReceives().stream().filter(o -> !(o instanceof AuthAndHeartbeat))
                            .forEach(p -> p.onReceive(pack));
                }
                logger.log("WebHook服务响应: " + resp);
                exchange.sendResponseHeaders(200, resp.length());
                exchange.getResponseBody().write(resp.getBytes("UTF-8"));
            });
            server.start();
            logger.info(String.format(BaseConnectedEvent.FORMAT_SERVER, config.getAppid()));
        } catch (IOException e) {
            logger.error("在WebHook服务启动时失败\n" + getExceptionLine(e));
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
