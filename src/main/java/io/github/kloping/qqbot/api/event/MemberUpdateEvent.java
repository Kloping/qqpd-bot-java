package io.github.kloping.qqbot.api.event;

import io.github.kloping.qqbot.entities.qqpd.MemberWithGuildID;

/**
 * 成员信息更新事件
 *
 * @author github.kloping
 */
public interface MemberUpdateEvent extends GuildEvent {
    /**
     * 被修改的成员
     *
     * @return
     */
    MemberWithGuildID getMember();
}
