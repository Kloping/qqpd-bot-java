package io.github.kloping.qqbot.impl.exc;

import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.exc.RequestException;
import io.github.kloping.qqbot.entities.exc.QBotError;

/**
 * @author github.kloping
 */
public class TokenExpireException extends RequestException {


    private QBotError error;

    public TokenExpireException(int code, String body, String url, String method) {
        super(code, body, url, method);
    }
}
