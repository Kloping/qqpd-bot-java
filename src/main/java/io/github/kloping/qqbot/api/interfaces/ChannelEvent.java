package io.github.kloping.qqbot.api.interfaces;

import io.github.kloping.qqbot.api.qqpd.Channel;

/**
 * @author github.kloping
 */
public interface ChannelEvent extends GuildEvent {

    /**
     * 事件所在子频道
     *
     * @return
     */
    Channel getChannel();
}
