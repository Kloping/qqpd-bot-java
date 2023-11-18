package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.api.event.ChannelEvent;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.message.EmojiReaction;

/**
 * 消息表情表态事件接口
 *
 * @author github.kloping
 */
public interface MessageReactionEvent extends ChannelEvent, MessageEvent<Member> {

    /**
     * get MessageReaction
     *
     * @return
     */
    EmojiReaction getMessageReaction();
}
