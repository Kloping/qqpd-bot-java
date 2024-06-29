package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.qqpd.v2.Contact;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;

/**
 * @author github.kloping
 */
public interface GroupMessageEvent extends GroupEvent, MessageEvent<Contact,Group>, MessageV2Event, SenderV2 {
    /**
     * 发送环境
     *
     * @return
     */
    Group getSubject();


    /**
     * 设置消息序列号并返回原序列号
     * @param seq
     * @return
     */
    Integer setMsgSeq(Integer seq);
}
