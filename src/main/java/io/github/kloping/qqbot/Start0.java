package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.annotations.CommentScan;

import java.util.HashMap;
import java.util.Map;

import static io.github.kloping.qqbot.Starter.AUTH_ID;

/**
 * @author github.kloping
 */
@CommentScan(path = "io.github.kloping.qqbot")
public class Start0 {

    public Map<String, String> headers = new HashMap();

    public Map<String, String> getHeaders() {
        if (headers.isEmpty()) {
            headers.put("Authorization", Resource.contextManager.getContextEntity(String.class, AUTH_ID));
        }
        return headers;
    }
}
