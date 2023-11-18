package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.entities.qqpd.Member;

/**
 * @author github.kloping
 */
public interface MessageDeleteEvent extends MessageEvent<Member> {
    /**
     * 事件 操作者ID
     *
     * @return
     */
    String getOpUserId();

    /**
     * 被撤回者ID
     *
     * @return
     */
    String getAuthorId();
}
