package io.github.kloping.qqbot.api.message;

import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class MessageReference {
    private String message_id;

    public MessageReference(String message_id) {
        this.message_id = message_id;
    }
}
