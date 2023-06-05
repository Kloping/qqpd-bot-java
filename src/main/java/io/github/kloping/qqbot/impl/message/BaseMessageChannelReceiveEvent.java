package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * @author github.kloping
 */
public class BaseMessageChannelReceiveEvent extends BaseMessageEvent implements MessageChannelReceiveEvent {
    public BaseMessageChannelReceiveEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo,bot);
        this.content = getRawMessage().getContent();
        this.channelId = getChannel().getId();
    }

    protected String content;
    protected String channelId;

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getChannelId() {
        return channelId;
    }

    @Override
    public String toString() {
        return String.format("[channel(%s)]member(%s)=>%s", getChannel().getName(), getSender().getNick(), getContent());
    }

}