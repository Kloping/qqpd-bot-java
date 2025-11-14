package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.message.BaseMessageDeleteEvent;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;

/**
 * @author github.kloping
 */
@Entity
public class MessageDeleteEventRegister implements Events.EventRegister {
    @AutoStandAfter
    private void r3(Events events) {
        events.register("MESSAGE_DELETE", this).register("PUBLIC_MESSAGE_DELETE", this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t,JSONObject mateData, RawMessage message) {
        Event event = null;
        event = new BaseMessageDeleteEvent(message, mateData, bot);
        return event;
    }
}
