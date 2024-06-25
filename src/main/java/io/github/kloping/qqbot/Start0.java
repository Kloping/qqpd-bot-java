package io.github.kloping.qqbot;

import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.CommentScan;
import io.github.kloping.spt.interfaces.component.ContextManager;
import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.http.AuthV2Base;
import io.github.kloping.qqbot.http.data.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
@CommentScan(path = "io.github.kloping.qqbot")
public class Start0 {

    public Map<String, String> headers = new HashMap();
    public Map<String, String> v2headers = new HashMap();

    @AutoStand
    ContextManager contextManager;

    public Map<String, String> getHeaders() {
        if (headers.isEmpty()) {
            headers.put("Authorization", contextManager.getContextEntity(String.class, Starter.AUTH_ID));
            headers.put("Accept-Encoding", "application/json");
        }
        return headers;
    }

    public Map<String, String> getV2Headers() {
        if (isExpired(token)) v2headers.clear();
        if (v2headers.isEmpty()) {
            String secret = contextManager.getContextEntity(String.class, Starter.SECRET_ID);
            if (Judge.isEmpty(secret)) return v2headers;
            v2headers.put("Authorization", String.format("QQBot %s", getV2Token(secret)));
            v2headers.put("X-Union-Appid", contextManager.getContextEntity(String.class, Starter.APPID_ID));
        }
        return v2headers;
    }

    private boolean isExpired(Token token) {
        return token == null || token.isExpired();
    }

    @AutoStand
    AuthV2Base authV2Base;

    private Token token;

    private String getV2Token(String secret) {
        token = authV2Base.auth(
                String.format("{\"appId\": \"%s\",\"clientSecret\": \"%s\"}\n", contextManager.getContextEntity(String.class, Starter.APPID_ID), secret)
                , Channel.SEND_MESSAGE_HEADERS
        );
        token.setT0(System.currentTimeMillis());
        return token.getAccess_token();
    }
}