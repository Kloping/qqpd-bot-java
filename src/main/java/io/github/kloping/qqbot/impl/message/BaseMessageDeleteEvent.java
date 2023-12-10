package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageDeleteEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * @author github.kloping
 */
public class BaseMessageDeleteEvent extends BaseMessageReceiveEvent implements MessageDeleteEvent {
    public BaseMessageDeleteEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(jo.getJSONObject("message").toJavaObject(RawMessage.class), jo, bot);
        opUserId = getMetadata().getJSONObject("op_user").getString("id");
    }

    private String opUserId;
    private String authorId;

    @Override
    public String getAuthorId() {
        return getRawMessage().getAuthor().getId();
    }

    @Override
    public String getOpUserId() {
        return opUserId;
    }

    @Override
    public String getClassName() {
        return "MessageDeleteEvent";
    }
}