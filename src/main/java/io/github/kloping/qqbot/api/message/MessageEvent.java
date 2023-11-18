package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.api.Sender;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Contact;

/**
 * 消息事件接口
 *
 * @author github.kloping
 */
public interface MessageEvent<T extends Contact> extends Event, Sender {
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
    T getSender();

    /**
     * 将消息转为 MessageChain
     *
     * @return
     */
    MessageChain getMessage();
}
