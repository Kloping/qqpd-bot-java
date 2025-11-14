package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.api.Reactive;

/**
 * 消息接收事件
 *
 * @author github.kloping
 */
public interface MessageChannelReceiveEvent extends MessageReceiveEvent, Reactive {
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
