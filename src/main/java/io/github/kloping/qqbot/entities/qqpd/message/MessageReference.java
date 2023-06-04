package io.github.kloping.qqbot.entities.qqpd.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 引用消息
 *
 * @author github.kloping
 */
@Data
public class MessageReference {
    @JSONField(name = "message_id")
    private String messageId;
    @JSONField(name = "ignore_get_message_error")
    private Boolean ignoreGetMessageError = false;

    public MessageReference(String messageId) {
        this.messageId = messageId;
    }
}
