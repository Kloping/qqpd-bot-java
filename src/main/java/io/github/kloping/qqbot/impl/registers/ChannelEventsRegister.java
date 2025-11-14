package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Common;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.BaseChannelUpdateEvent;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;

/**
 * @author github.kloping
 */
@Entity
public class ChannelEventsRegister implements Events.EventRegister {

    public static final String CHANNEL_CREATE = "CHANNEL_CREATE";
    public static final String CHANNEL_DELETE = "CHANNEL_DELETE";

    @AutoStandAfter
    private void r8(Events events) {
        events.register("CHANNEL_UPDATE", this).register(CHANNEL_CREATE, this).register(CHANNEL_DELETE, this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        BaseChannelUpdateEvent event = new BaseChannelUpdateEvent(mateData, bot);
        if (CHANNEL_CREATE.equals(t)) {
            Common.GUILD_CHANNEL_TEMP.get(event.getChannel().getGuildId()).put(event.getChannel().getId(), event.getChannel());
        } else if (CHANNEL_DELETE.equals(t)) {
            Common.GUILD_CHANNEL_TEMP.get(event.getChannel().getGuildId()).remove(event.getChannel().getId());
        }
        return event;
    }
}
