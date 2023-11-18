package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.api.DirectSender;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.message.DirectMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;

/**
 * 私信消息接收事件
 *
 * @author github.kloping
 */
public interface MessageDirectReceiveEvent extends MessageReceiveEvent, DirectSender {
    /**
     * 来源guild
     *
     * @return
     */
    String getSrcGuildId();

    /**
     * 来源guild
     *
     * @return
     */
    Guild getSrcGuild();

    /**
     * 获取私信消息
     *
     * @return
     */
    DirectMessage getDirectMessage();

    /**
     * 替换默认
     *
     * @param text
     * @return
     */
    @Override
    default Result<ActionResult> send(String text) {
        return sendDirect(text);
    }

    /**
     * 替换默认
     *
     * @param text
     * @param message
     * @return
     */
    @Override
    default Result<ActionResult> send(String text, RawMessage message) {
        return sendDirect(text, message);
    }

    /**
     * 替换默认
     *
     * @param packet
     * @return
     */
    @Override
    default Result<ActionResult> send(MessagePacket packet) {
        return sendDirect(packet);
    }

    /**
     * 替换默认
     *
     * @param msg
     * @return
     */
    @Override
    default Result<ActionResult> send(RawPreMessage msg) {
        return sendDirect(msg);
    }
}
