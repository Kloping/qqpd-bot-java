package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.BaseGuildUpdateEvent;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;
import lombok.extern.slf4j.Slf4j;

/**
 * @author github.kloping
 */
@Entity
@Slf4j
public class GuildEventsRegister implements Events.EventRegister {
    public static final String GUILD_CREATE = "GUILD_CREATE";
    public static final String GUILD_DELETE = "GUILD_DELETE";

    @AutoStandAfter
    private void r4(Events events) {
        events.register("GUILD_UPDATE", this).register(GUILD_CREATE, this).register(GUILD_DELETE, this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        BaseGuildUpdateEvent event = null;
        event = new BaseGuildUpdateEvent(mateData, bot);
        if (GUILD_CREATE.equals(t)) {
            log.info("{} Event Bot Join Guild[{}({})]", t, event.getGuild().getName(), event.getGuild().getId());
        } else if (GUILD_DELETE.equals(t)) {
            log.info("{} Event Exit From Guild[{}({})]", t, event.getGuild().getName(), event.getGuild().getId());
            bot.delGuild(event.getGuild());
        }
        return event;
    }
}
