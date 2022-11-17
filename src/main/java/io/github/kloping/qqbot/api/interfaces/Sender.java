package io.github.kloping.qqbot.api.interfaces;

import io.github.kloping.qqbot.api.message.Message;
import io.github.kloping.qqbot.api.message.PreMessage;
import io.github.kloping.qqbot.api.message.audited.MessageAudited;

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
    public MessageAudited send(String text);

    /**
     * 以JSON方式发送文本消息并引用指定消息
     *
     * @param text
     * @param message
     * @return
     */
    public MessageAudited sendAndReply(String text, Message message);


    /**
     * 以header方式发送文本消息
     *
     * @param text
     * @return
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public default String send0(String text) {
        return null;
    }

    /**
     * 自定义消息
     *
     * @param msg
     * @return
     */
    public MessageAudited send(PreMessage msg);
}
