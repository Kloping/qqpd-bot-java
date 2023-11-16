package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupMessageEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class GroupEventsRegister implements Events.EventRegister {
    public static final String GROUP_AT_MESSAGE_CREATE = "GROUP_AT_MESSAGE_CREATE";

    @AutoStandAfter
    private void r4(Events events) {
        events.register(GROUP_AT_MESSAGE_CREATE, this);
    }

    @AutoStand
    Bot bot;

    @AutoStand
    Logger logger;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        Event event = null;
        if (GROUP_AT_MESSAGE_CREATE.equals(t)) {
            event = new BaseGroupMessageEvent(message, mateData, bot);
        } else {
        }
        return event;
    }
}
