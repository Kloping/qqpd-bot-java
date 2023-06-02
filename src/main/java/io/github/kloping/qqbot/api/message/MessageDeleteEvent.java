package io.github.kloping.qqbot.api.message;

/**
 * @author github.kloping
 */
public interface MessageDeleteEvent extends MessageEvent {
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
