package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.entities.qqpd.v2.Friend;

/**
 * @author github.kloping
 */
public interface FriendMessageEvent extends FriendEvent, MessageV2Event {
    @Override
    Friend getSender();

    @Override
    Friend getSubject();
}
