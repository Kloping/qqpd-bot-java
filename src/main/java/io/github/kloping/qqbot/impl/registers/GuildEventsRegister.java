package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.qqbot.api.Event;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.entitys.qqpd.message.Message;
import io.github.kloping.qqbot.impl.BaseGuildUpdateEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class GuildEventsRegister implements Events.EventRegister {
    @AutoStandAfter
    private void r4(Events events) {
        events.register("GUILD_UPDATE", this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(JSONObject mateData, Message message) {
        Event event = null;
        event = new BaseGuildUpdateEvent(mateData, bot);
        return event;
    }
}
