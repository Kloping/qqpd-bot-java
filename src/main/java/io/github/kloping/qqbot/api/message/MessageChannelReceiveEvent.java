package io.github.kloping.qqbot.api.message;

/**
 * 消息接收事件
 *
 * @author github.kloping
 */
public interface MessageChannelReceiveEvent extends MessageEvent {
    /**
     * 消息内容
     *
     * @return
     */
    String getContent();

    /**
     * 获取子频道ID
     *
     * @return
     */
    String getChannelId();
}
