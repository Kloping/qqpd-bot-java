package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entities.qqpd.Guild;

/**
 * 频道事件
 *
 * @author github.kloping
 */
public interface GuildEvent extends Event {
    /**
     * 事件所在频道
     *
     * @return
     */
    Guild getGuild();
}
