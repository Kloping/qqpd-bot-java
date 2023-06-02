package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageReceiveEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.Message;

/**
 * @author github.kloping
 */
public class BaseMessageReceiveEvent extends BaseMessageEvent implements MessageReceiveEvent {
    public BaseMessageReceiveEvent() {
    }

    public BaseMessageReceiveEvent(Message message, JSONObject jo, Bot bot) {
        super(message, jo,bot);
        this.content = getMessage().getContent() == null ? "" : getMessage().getContent();
    }

    protected String content;

    @Override
    public String getContent() {
        return content;
    }
}