package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.qqbot.api.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.Message;
import io.github.kloping.qqbot.impl.message.BaseMessageDeleteEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class MessageDeleteEventRegister implements Events.EventRegister {
    @AutoStandAfter
    private void r3(Events events) {
        events.register("MESSAGE_DELETE", this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t,JSONObject mateData, Message message) {
        Event event = null;
        event = new BaseMessageDeleteEvent(message, mateData, bot);
        return event;
    }
}
