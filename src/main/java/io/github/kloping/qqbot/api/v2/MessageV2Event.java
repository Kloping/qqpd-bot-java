package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.Sender;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.v2.Contact;
import io.github.kloping.qqbot.http.data.V2Result;

/**
 * @author github.kloping
 */
public interface MessageV2Event extends Event, Sender, V2Event {
    /**
     * 发送纯文本
     *
     * @param text
     * @return
     */
    V2Result sendMessage(String text);

    V2Result sendMessage(SendAble msg);

    /**
     * 当前 因为腾讯服务器原因 消息中不存在at类型
     * 消息组
     *
     * @return
     */
    MessageChain getMessage();

    /**
     * 发送者
     *
     * @return
     */
    Contact getSender();

    /**
     * 发送环境
     *
     * @return
     */
    Contact getSubject();


    /**
     * 设置消息序列号并返回原序列号
     * @param seq
     * @return
     */
    Integer setMsgSeq(Integer seq);
}
