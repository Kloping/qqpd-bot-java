import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.github.kloping.qqbot.utils.HttpUtils;
import org.jsoup.Connection;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

/**
 * {@link HttpUtils#delete} 回归测试。
 * <p>SpringTool 0.7.2-L2 的 {@code @HttpClient} 代理会把 DELETE 错误地发送为 GET，
 * 指令面板删除能力因此改用该方法直接发送 DELETE，这里验证其确实发送 DELETE 方法。</p>
 */
public class HttpUtilsTest {

    @Test
    public void sendsDeleteWithHeaders() throws Exception {
        AtomicReference<String> method = new AtomicReference<>();
        AtomicReference<String> auth = new AtomicReference<>();
        HttpServer server = serve(ex -> {
            method.set(ex.getRequestMethod());
            auth.set(ex.getRequestHeaders().getFirst("Authorization"));
            write(ex, 200, "{}");
        });
        try {
            Connection.Response response = HttpUtils.delete(
                    url(server, "/v2/panels/p1"),
                    Collections.singletonMap("Authorization", "QQBot token"));
            assertEquals(200, response.statusCode());
            assertEquals("DELETE", method.get());
            assertEquals("QQBot token", auth.get());
        } finally {
            server.stop(0);
        }
    }

    @Test
    public void wrapsHttpErrors() throws Exception {
        HttpServer server = serve(ex -> {
            ex.sendResponseHeaders(404, -1);
            ex.close();
        });
        try {
            HttpUtils.delete(url(server, "/v2/panels/missing"), Collections.emptyMap());
            fail("expected RuntimeException for 404 response");
        } catch (RuntimeException expected) {
            assertTrue(String.valueOf(expected.getMessage()), expected.getMessage().contains("/v2/panels/missing"));
        } finally {
            server.stop(0);
        }
    }

    private static String url(HttpServer server, String path) {
        return "http://127.0.0.1:" + server.getAddress().getPort() + path;
    }

    private static void write(HttpExchange exchange, int status, String body) throws java.io.IOException {
        byte[] bytes = body.getBytes("UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }

    private static HttpServer serve(HttpHandler handler) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", handler);
        server.start();
        return server;
    }
}
