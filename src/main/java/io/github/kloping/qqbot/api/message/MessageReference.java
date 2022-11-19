package io.github.kloping.qqbot.api.message;

import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class MessageReference {
    private String messageId;

    public MessageReference(String messageId) {
        this.messageId = messageId;
    }
}
