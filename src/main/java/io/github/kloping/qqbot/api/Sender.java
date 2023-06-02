package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entities.qqpd.message.Message;
import io.github.kloping.qqbot.entities.qqpd.message.PreMessage;
import io.github.kloping.qqbot.entities.qqpd.message.audited.MessageAudited;
import io.github.kloping.qqbot.impl.MessagePacket;

/**
 * 发送者(消息)接口
 *
 * @author github.kloping
 */
public interface Sender {

    /**
     * 以JSON方式发送文本消息
     *
     * @param text
     * @return
     */
    MessageAudited send(String text);

    /**
     * 以JSON方式发送文本消息并引用指定消息
     *
     * @param text
     * @param message
     * @return
     */
    MessageAudited send(String text, Message message);

    /**
     * 以自定义方式发送消息
     *
     * @param packet
     * @return
     */
    MessageAudited send(MessagePacket packet);

    /**
     * 以header方式发送文本消息
     *
     * @param text
     * @return
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    default String send0(String text) {
        return null;
    }

    /**
     * 自定义消息
     *
     * @param msg
     * @return
     */
    MessageAudited send(PreMessage msg);
}
