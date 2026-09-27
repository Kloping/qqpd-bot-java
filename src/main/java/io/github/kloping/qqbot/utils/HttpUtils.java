package io.github.kloping.qqbot.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * HTTP 请求工具。
 *
 * <p>SDK 的 {@code @HttpClient} 注解代理底层依赖 SpringTool 的请求实现，
 * 该实现仅在 {@link Connection.Method#hasBody()} 为 true 时才会设置请求方法，
 * 而 DELETE 的 hasBody() 恒为 false，会被代理错误地以 GET 发送。
 * 因此需要直接用 jsoup 发送 DELETE 请求。</p>
 *
 * @author github.kloping
 */
public final class HttpUtils {

    private HttpUtils() {
    }

    /**
     * 发送 DELETE 请求。
     *
     * @param url     完整请求地址
     * @param headers 请求头，通常为 {@code Start0#getHeaders()}
     * @return jsoup 响应
     * @throws RuntimeException 请求失败或服务端返回错误状态码时抛出
     */
    public static Connection.Response delete(String url, Map<String, String> headers) {
        try {
            return Jsoup.connect(url)
                    .headers(headers == null ? Collections.emptyMap() : headers)
                    .method(Connection.Method.DELETE)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException("DELETE request failed: " + url, e);
        }
    }
}
