package io.github.kloping.qqbot.api.data.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.interfaces.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.api.qqpd.message.Message;

/**
 * @author github.kloping
 */
public class BaseMessageChannelReceiveEvent extends BaseMessageEvent implements MessageChannelReceiveEvent {
    public BaseMessageChannelReceiveEvent() {
    }

    public BaseMessageChannelReceiveEvent(Message message, JSONObject jo) {
        super(message, jo);
        this.content = getMessage().getContent() == null ? "" : getMessage().getContent();
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
}