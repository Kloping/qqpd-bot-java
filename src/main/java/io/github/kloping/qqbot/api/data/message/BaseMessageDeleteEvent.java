package io.github.kloping.qqbot.api.data.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.interfaces.message.MessageDeleteEvent;
import io.github.kloping.qqbot.api.qqpd.message.Message;

/**
 * @author github.kloping
 */
public class BaseMessageDeleteEvent extends BaseMessageReceiveEvent implements MessageDeleteEvent {
    public BaseMessageDeleteEvent(Message message, JSONObject jo) {
        super(jo.getJSONObject("message").toJavaObject(Message.class), jo);
        opUserId = getMetadata().getJSONObject("op_user").getString("id");
    }

    private String opUserId;
    private String authorId;

    @Override
    public String getAuthorId() {
        return getMessage().getAuthor().getId();
    }

    @Override
    public String getOpUserId() {
        return opUserId;
    }
}