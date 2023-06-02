package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Bean;
import io.github.kloping.MySpringTool.annotations.CommentScan;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.entitys.qqpd.Guild;
import io.github.kloping.qqbot.entitys.qqpd.User;
import io.github.kloping.qqbot.http.GuildBase;
import io.github.kloping.qqbot.http.UserBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
@CommentScan(path = "io.github.kloping.qqbot")
public class Start0 {

    public Map<String, String> headers = new HashMap();

    @AutoStand
    ContextManager contextManager;

    public Map<String, String> getHeaders() {
        if (headers.isEmpty()) {
            headers.put("Authorization", contextManager.getContextEntity(String.class, Starter.AUTH_ID));
        }
        return headers;
    }
}