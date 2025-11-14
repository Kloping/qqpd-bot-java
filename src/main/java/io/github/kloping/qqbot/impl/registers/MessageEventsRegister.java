package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.message.BaseMessageChannelReceiveEvent;
import io.github.kloping.qqbot.impl.message.BaseMessageContainsAtEvent;
import io.github.kloping.qqbot.impl.message.BaseMessageDirectReceiveEvent;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;

/**
 * @author github.kloping
 */
@Entity
public class MessageEventsRegister implements Events.EventRegister {
    @AutoStandAfter
    public void r2(Events events) {
        events.register(MESSAGE_CREATE, this).register(AT_MESSAGE_CREATE, this).register("DIRECT_MESSAGE_CREATE", this);
    }

    public static final String AT_MESSAGE_CREATE = "AT_MESSAGE_CREATE";
    public static final String MESSAGE_CREATE = "MESSAGE_CREATE";

    @AutoStand
    Bot bot;

    private String msgId = null;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage msg) {
        if (msg.getId().equals(msgId)) return null;
        else msgId = msg.getId();
        Event event = null;
        if (msg.getMentions() != null && msg.getMentions().length > 0) {
            event = new BaseMessageContainsAtEvent(msg, mateData, bot);
        } else if (msg.getSrcGuildId() != null && !msg.getSrcGuildId().isEmpty()) {
            event = new BaseMessageDirectReceiveEvent(msg, mateData, bot);
            msg.getAuthor().setBot(false);
            msg.getMember().setNick(msg.getAuthor().getUsername());
            msg.getMember().setUser(msg.getAuthor());
        } else {
            event = new BaseMessageChannelReceiveEvent(msg, mateData, bot);
        }
        return event;
    }
}
