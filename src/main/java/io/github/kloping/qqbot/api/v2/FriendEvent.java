package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.entities.qqpd.v2.Contact;
import io.github.kloping.qqbot.entities.qqpd.v2.Friend;

/**
 * @author github.kloping
 */
public interface FriendEvent extends V2Event {
    /**
     * get friend
     *
     * @return
     */
    Friend getFriend();
}
