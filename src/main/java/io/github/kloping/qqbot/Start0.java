package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.CommentScan;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.qqbot.http.BotBase;

import java.util.HashMap;
import java.util.Map;

import static io.github.kloping.qqbot.Starter.AUTH_ID;

/**
 * @author github.kloping
 */
@CommentScan(path = "io.github.kloping.qqbot")
public class Start0 {

    @AutoStand
    ContextManager contextManager;

    public Map<String, String> headers = new HashMap();

    public Map<String, String> getHeaders() {
        if (headers.isEmpty()) {
            headers.put("Authorization", contextManager.getContextEntity(String.class, AUTH_ID));
        }
        return headers;
    }
}
