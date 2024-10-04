package io.github.kloping.qqbot.api.exc;

import io.github.kloping.qqbot.entities.exc.QBotError;
import lombok.Getter;
import lombok.Setter;

/**
 * 发送请求时抛出的运行是异常
 * http resp code != 200
 *
 * @author github.kloping
 */
@Getter
public class RequestException extends RuntimeException {
    private final int code;
    private final String body;
    private final String url;
    private final String method;
    @Setter
    protected QBotError data;

    public RequestException(int code, String body, String url,String method) {
        super(String.format("The request error response url [%s] code is [%s] (%s)", url, code, body));
        this.code = code;
        this.body = body;
        this.url = url;
        this.method = method;
    }
}
