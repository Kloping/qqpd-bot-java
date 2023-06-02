package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entitys.qqpd.Member;
import io.github.kloping.qqbot.entitys.qqpd.MemberWithGuildID;

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
