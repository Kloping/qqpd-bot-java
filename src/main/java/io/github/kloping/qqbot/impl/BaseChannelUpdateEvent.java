package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.ChannelUpdateEvent;
import io.github.kloping.qqbot.entities.Bot;

/**
 * @author github.kloping
 */
public class BaseChannelUpdateEvent extends BaseChannelEvent implements ChannelUpdateEvent {
    public BaseChannelUpdateEvent(JSONObject jo, Bot bot) {
        super(jo, bot);
    }

    @Override
    public String toString() {
        return String.format("子频道'%s'信息更新", getChannel().getName());
    }
}
