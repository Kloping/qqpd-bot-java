package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.qqpd.v2.Contact;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import io.github.kloping.qqbot.entities.qqpd.v2.Member;

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
     * 获取发送者
     * @return
     */
    @Override
    Member getSender();
}
