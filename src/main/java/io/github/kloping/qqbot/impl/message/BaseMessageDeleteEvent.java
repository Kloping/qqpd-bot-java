package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageDeleteEvent;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.entitys.qqpd.message.Message;

/**
 * @author github.kloping
 */
public class BaseMessageDeleteEvent extends BaseMessageReceiveEvent implements MessageDeleteEvent {
    public BaseMessageDeleteEvent(Message message, JSONObject jo, Bot bot) {
        super(jo.getJSONObject("message").toJavaObject(Message.class), jo, bot);
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