package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.HttpClientConfig;
import io.github.kloping.qqbot.api.event.ChannelEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Channel;

/**
 * @author github.kloping
 */
public class BaseChannelEvent extends BaseGuildEvent implements ChannelEvent {
    private Channel channel;

    public BaseChannelEvent(JSONObject jo, Bot bot) {
        super(jo, bot);
        channel = jo.toJavaObject(Channel.class);
        channel.setBot(bot);
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public String getId() {
        return getMetadata().get("id").toString();
    }
}
