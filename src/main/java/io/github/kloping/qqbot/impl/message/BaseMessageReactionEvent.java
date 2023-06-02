package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageReactionEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.message.Message;
import io.github.kloping.qqbot.entities.qqpd.message.MessageReaction;

/**
 * @author github.kloping
 */
public class BaseMessageReactionEvent extends BaseMessageEvent implements MessageReactionEvent {
    public BaseMessageReactionEvent(Message message, JSONObject jo, Bot bot, MessageReaction reaction) {
        super(message, jo, bot);
        this.reaction = reaction;
    }

    private MessageReaction reaction;

    private Boolean isAdd;

    public MessageReaction getReaction() {
        return reaction;
    }

    public void setReaction(MessageReaction reaction) {
        this.reaction = reaction;
    }

    public Boolean getAdd() {
        return isAdd;
    }

    public void setAdd(Boolean add) {
        isAdd = add;
    }

    @Override
    public MessageReaction getMessageReaction() {
        return reaction;
    }

    @Override
    public String toString() {
        Member member = getGuild().getMember(reaction.getUserId());
        return String.format("%s'%s'表情(%s)", member.getNick(), isAdd ? "添加" : "移除", reaction.getEmoji().getText());
    }
}