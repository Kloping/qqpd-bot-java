package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.api.ChannelEvent;
import io.github.kloping.qqbot.api.Sender;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * 消息事件接口
 *
 * @author github.kloping
 */
public interface MessageEvent extends ChannelEvent, Sender {
    /**
     * 获取消息
     *
     * @return
     */
    RawMessage getRawMessage();

    /**
     * 消息事件发送者
     *
     * @return
     */
    Member getSender();
}
