package io.github.kloping.qqbot.api.v2;

/**
 * @author github.kloping
 */
public interface GroupOpRobotEvent extends GroupEvent {
    String getOpMemberOpenid();

    Long getTimestamp();
}
