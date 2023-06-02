package io.github.kloping.qqbot.entitys.qqpd.message;

import lombok.Data;

/**
 * 引用消息
 *
 * @author github.kloping
 */
@Data
public class MessageReference {
    private String messageId;

    public MessageReference(String messageId) {
        this.messageId = messageId;
    }
}
