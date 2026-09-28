package io.github.kloping.qqbot;

import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.http.AuthV2Base;
import io.github.kloping.qqbot.http.data.AccessToken;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.ComponentScan;
import io.github.kloping.spt.interfaces.component.ContextManager;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
@ComponentScan(value = "io.github.kloping.qqbot")
public class Start0 {

    public Map<String, String> headers = new HashMap();

    @AutoStand
    ContextManager contextManager;

    //ALL req token header
    public synchronized Map<String, String> getHeaders() {
        if (headers.isEmpty() || isExpired(accessToken)) updateToken();
        return headers;
    }

    private boolean isExpired(AccessToken accessToken) {
        return accessToken == null || accessToken.isExpired();
    }

    private AccessToken accessToken;

    public String getAccessToken() {
        if (isExpired(accessToken)) updateToken();
        return accessToken.getAccess_token();
    }

    public void updateToken() {
        headers.clear();
        String v2token = getV2Token();
        headers.put("Authorization", String.format("QQBot %s", v2token));
        headers.put("Accept-Encoding", "application/json");
        headers.put("X-Union-Appid", contextManager.getContextEntity(String.class, Starter.APPID_ID));
    }

    @AutoStand
    AuthV2Base authV2Base;

    private String getV2Token() {
        String appid = contextManager.getContextEntity(String.class, Starter.APPID_ID);
        String secret = contextManager.getContextEntity(String.class, Starter.SECRET_ID);
        accessToken = authV2Base.auth(new AppidAndSecret(appid, secret),
                Channel.SEND_MESSAGE_HEADERS).setT0(System.currentTimeMillis());
        return accessToken.getAccess_token();
    }

    @Data
    public static class AppidAndSecret {
        public String appId;
        public String clientSecret;

        public AppidAndSecret(String appid, String secret) {
            this.appId = appid;
            this.clientSecret = secret;
        }
    }
}