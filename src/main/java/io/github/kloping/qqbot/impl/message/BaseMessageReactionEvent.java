package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageReactionEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.EmojiReaction;
import lombok.Getter;

/**
 * @author github.kloping
 */
public class BaseMessageReactionEvent extends BaseMessageEvent implements MessageReactionEvent {
    public BaseMessageReactionEvent(RawMessage message, JSONObject jo,
                                    Bot bot, EmojiReaction reaction) {
        super(message, jo, bot);
        this.reaction = reaction;
    }

    @Getter
    private EmojiReaction reaction;

    private Boolean isAdd;

    public void setReaction(EmojiReaction reaction) {
        this.reaction = reaction;
    }

    public Boolean getAdd() {
        return isAdd;
    }

    public void setAdd(Boolean add) {
        isAdd = add;
    }

    @Override
    public EmojiReaction getMessageReaction() {
        return reaction;
    }

    @Override
    public String toString() {
        Member member =
                getGuild().getMember(reaction.getUserId());
        return String.format("%s'%s'表情(%s)", member.getNick(), isAdd ? "添加" : "移除", reaction.getEmoji().getText());
    }
}