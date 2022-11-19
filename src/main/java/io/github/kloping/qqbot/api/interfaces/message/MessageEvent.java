package io.github.kloping.qqbot.api.interfaces.message;

import io.github.kloping.qqbot.api.interfaces.ChannelEvent;
import io.github.kloping.qqbot.api.qqpd.Member;
import io.github.kloping.qqbot.api.qqpd.interfaces.DirectSender;
import io.github.kloping.qqbot.api.qqpd.interfaces.Sender;
import io.github.kloping.qqbot.api.qqpd.message.Message;

/**
 * 消息事件接口
 *
 * @author github.kloping
 */
public interface MessageEvent extends ChannelEvent, DirectSender, Sender {
    /**
     * 获取消息
     *
     * @return
     */
    Message getMessage();

    /**
     * 消息事件发送者
     *
     * @return
     */
    Member getSender();
}
