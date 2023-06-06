package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
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
    ActionResult send(String text);

    /**
     * 以JSON方式发送文本消息并引用指定消息
     *
     * @param text
     * @param message
     * @return
     */
    ActionResult send(String text, RawMessage message);

    /**
     * 以自定义方式发送消息
     *
     * @param packet
     * @return
     */
    ActionResult send(MessagePacket packet);

    /**
     * 自定义消息
     *
     * @param msg
     * @return
     */
    ActionResult send(RawPreMessage msg);

    /**
     * 以各种方式 达到想要发送的效果
     *
     * @param msg
     * @return
     */
    ActionResult send(SendAble msg);
}
