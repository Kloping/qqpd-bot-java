package io.github.kloping.qqbot.api.message;

/**
 * 消息存在AT事件
 *
 * @author github.kloping
 */
public interface MessageContainsAtEvent extends MessageReceiveEvent {
    /**
     * 获取消息中所有被AT者ID
     *
     * @return
     */
    String[] getAllAt();
}
