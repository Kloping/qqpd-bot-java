package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entitys.qqpd.message.Message;
import io.github.kloping.qqbot.entitys.qqpd.message.PreMessage;
import io.github.kloping.qqbot.entitys.qqpd.message.audited.MessageAudited;
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
    public MessageAudited sendDirect(String text);

    /**
     * 以JSON方式发送文本消息并引用指定消息
     *
     * @param text
     * @param message
     * @return
     */
    public MessageAudited sendDirect(String text, Message message);

    /**
     * 以自定义方式发送消息
     *
     * @param packet
     * @return
     */
    public MessageAudited sendDirect(MessagePacket packet);

    /**
     * 自定义消息
     *
     * @param msg
     * @return
     */
    public MessageAudited sendDirect(PreMessage msg);
}
