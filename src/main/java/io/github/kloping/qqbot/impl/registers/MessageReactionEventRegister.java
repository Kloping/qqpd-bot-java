package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.MessagePack;
import io.github.kloping.qqbot.entities.qqpd.message.MessageReaction;
import io.github.kloping.qqbot.impl.message.BaseMessageReactionEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class MessageReactionEventRegister implements Events.EventRegister {

    @AutoStand
    Bot bot;

    @AutoStandAfter
    private void r7(Events events) {
        events.register("MESSAGE_REACTION_ADD", this);
        events.register("MESSAGE_REACTION_REMOVE", this);
    }

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        BaseMessageReactionEvent event = null;
        MessageReaction reaction = mateData.toJavaObject(MessageReaction.class);
        JSONObject jo = mateData.getJSONObject("emoji");
        Integer type = jo.getInteger("type");
        String id = jo.getString("id");
        reaction.setEmoji(Emoji.valueOf(type, Integer.valueOf(id)));
        MessagePack pack = Resource.channelBase.getMessageById(reaction.getChannelId(), reaction.getTarget().getId());
        message = pack.getMessage();
        event = new BaseMessageReactionEvent(message, mateData, bot, reaction);
        switch (t) {
            case "MESSAGE_REACTION_ADD":
                event.setAdd(true);
                break;
            case "MESSAGE_REACTION_REMOVE":
                event.setAdd(false);
                break;
            default:
                break;
        }
        return event;
    }
}
