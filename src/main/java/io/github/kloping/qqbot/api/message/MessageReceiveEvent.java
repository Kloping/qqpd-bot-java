package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.api.event.ChannelEvent;
import io.github.kloping.qqbot.entities.qqpd.Member;

/**
 * 消息接收事件
 *
 * @author github.kloping
 */
public interface MessageReceiveEvent extends ChannelEvent, MessageEvent<Member> {
    /**
     * 消息内容
     *
     * @return
     */
    String getContent();
}
