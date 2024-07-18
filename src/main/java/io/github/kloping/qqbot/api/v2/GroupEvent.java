package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.entities.qqpd.v2.Group;

/**
 * @author github.kloping
 */
public interface GroupEvent extends V2Event {
    Group getGroup();

    String getGroupId();

    default String getGroupOpenId() {
        return getGroupId();
    }

    @Override
    default String getOpenId() {
        return getGroupOpenId();
    }
}
