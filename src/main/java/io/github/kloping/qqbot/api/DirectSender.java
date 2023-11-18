package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;

/**
 * 私信发送者(消息)接口
 *
 * @author github.kloping
 */
public interface DirectSender extends Sender {
    @Override
    default Result<ActionResult> send(String text) {
        return sendDirect(text);
    }

    @Override
    default Result<ActionResult> send(String text, RawMessage message) {
        return sendDirect(text, message);
    }

    @Override
    default Result<ActionResult> send(MessagePacket packet) {
        return sendDirect(packet);
    }

    @Override
    default Result<ActionResult> send(RawPreMessage msg) {
        return sendDirect(msg);
    }

    /**
     * 以JSON方式发送文本消息
     *
     * @param text
     * @return
     */
    Result<ActionResult> sendDirect(String text);

    /**
     * 以JSON方式发送文本消息并引用指定消息
     *
     * @param text
     * @param message
     * @return
     */
    Result<ActionResult> sendDirect(String text, RawMessage message);

    /**
     * 以自定义方式发送消息
     *
     * @param packet
     * @return
     */
    Result<ActionResult> sendDirect(MessagePacket packet);

    /**
     * 自定义消息
     *
     * @param msg
     * @return
     */
    Result<ActionResult> sendDirect(RawPreMessage msg);
}
