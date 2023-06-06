package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.impl.MessagePacket;

/**
 * 私信发送者(消息)接口
 *
 * @author github.kloping
 */
public interface DirectSender {

    /**
     * 以JSON方式发送文本消息
     *
     * @param text
     * @return
     */
    ActionResult sendDirect(String text);

    /**
     * 以JSON方式发送文本消息并引用指定消息
     *
     * @param text
     * @param message
     * @return
     */
    ActionResult sendDirect(String text, RawMessage message);

    /**
     * 以自定义方式发送消息
     *
     * @param packet
     * @return
     */
    ActionResult sendDirect(MessagePacket packet);

    /**
     * 自定义消息
     *
     * @param msg
     * @return
     */
    ActionResult sendDirect(RawPreMessage msg);
}
