package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.CommentScan;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;

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
            headers.put("Accept-Encoding", "application/json");
        }
        return headers;
    }
}