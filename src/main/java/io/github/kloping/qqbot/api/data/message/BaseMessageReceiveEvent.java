package io.github.kloping.qqbot.api.data.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.interfaces.message.MessageReceiveEvent;
import io.github.kloping.qqbot.api.qqpd.message.Message;

/**
 * @author github.kloping
 */
public class BaseMessageReceiveEvent extends BaseMessageEvent implements MessageReceiveEvent {
    public BaseMessageReceiveEvent() {
    }

    public BaseMessageReceiveEvent(Message message, JSONObject jo) {
        super(message, jo);
        this.content = message.getContent();
    }

    private String content;

    @Override
    public String getContent() {
        return content;
    }
}