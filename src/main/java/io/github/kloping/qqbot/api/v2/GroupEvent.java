package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;

/**
 * @author github.kloping
 */
public interface GroupEvent extends Event, V2Event {
    Group getGroup();

    String getGroupId();

    default String getGroupOpenId() {
        return getGroupId();
    }
}
