package io.github.kloping.qqbot;

import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.http.AuthV2Base;
import io.github.kloping.qqbot.http.data.Token;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.ComponentScan;
import io.github.kloping.spt.interfaces.component.ContextManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
@ComponentScan(value = "io.github.kloping.qqbot")
public class Start0 {

    public Map<String, String> headers = new HashMap();
    public Map<String, String> v2headers = new HashMap();

    @AutoStand
    ContextManager contextManager;

    public Map<String, String> getHeaders() {
        if (isExpired(token)) headers.clear();
        if (headers.isEmpty()) {
            headers.put("Authorization", String.format("QQBot %s", getV2Token()));
            headers.put("Accept-Encoding", "application/json");
        }
        return headers;
    }

    public Map<String, String> getV2Headers() {
        if (isExpired(token)) v2headers.clear();
        if (v2headers.isEmpty()) {
            v2headers.put("Authorization", String.format("QQBot %s", getV2Token()));
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

    private String getV2Token() {
        String appid = contextManager.getContextEntity(String.class, Starter.APPID_ID);
        String secret = contextManager.getContextEntity(String.class, Starter.SECRET_ID);
        token = authV2Base.auth(
                String.format("{\"appId\": \"%s\",\"clientSecret\": \"%s\"}\n", appid, secret)
                , Channel.SEND_MESSAGE_HEADERS);
        token.setT0(System.currentTimeMillis());
        return token.getAccess_token();
    }

    public String getAccessToken() {
        return isExpired(token) ? getV2Token() : token.getAccess_token();
    }
}