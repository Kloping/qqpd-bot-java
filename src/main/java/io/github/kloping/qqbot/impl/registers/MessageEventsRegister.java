package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.qqbot.api.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.Message;
import io.github.kloping.qqbot.impl.message.BaseMessageChannelReceiveEvent;
import io.github.kloping.qqbot.impl.message.BaseMessageContainsAtEvent;
import io.github.kloping.qqbot.impl.message.BaseMessageDirectReceiveEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class MessageEventsRegister implements Events.EventRegister {
    @AutoStandAfter
    public void r2(Events events) {
        events.register("MESSAGE_CREATE", this);
        events.register("AT_MESSAGE_CREATE", this);
        events.register("DIRECT_MESSAGE_CREATE", this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(JSONObject mateData, Message msg) {
        Event event = null;
        if (msg.getMentions() != null && msg.getMentions().length > 0) {
            event = new BaseMessageContainsAtEvent(msg, mateData, bot);
        } else if (msg.getSrcGuildId() != null && !msg.getSrcGuildId().isEmpty()) {
            event = new BaseMessageDirectReceiveEvent(msg, mateData, bot);
        } else {
            event = new BaseMessageChannelReceiveEvent(msg, mateData, bot);
        }
        return event;
    }
}
