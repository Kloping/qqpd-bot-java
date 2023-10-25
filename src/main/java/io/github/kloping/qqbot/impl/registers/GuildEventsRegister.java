package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.BaseGuildUpdateEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class GuildEventsRegister implements Events.EventRegister {
    public static final String GUILD_CREATE = "GUILD_CREATE";
    public static final String GUILD_DELETE = "GUILD_DELETE";

    @AutoStandAfter
    private void r4(Events events) {
        events.register("GUILD_UPDATE", this);
        events.register(GUILD_CREATE, this);
        events.register(GUILD_DELETE, this);
    }

    @AutoStand
    Bot bot;

    @AutoStand
    Logger logger;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        BaseGuildUpdateEvent event = null;
        event = new BaseGuildUpdateEvent(mateData, bot);
        if (GUILD_CREATE.equals(t)) {
            logger.info(String.format(t + " Event Bot Join Guild[%s(%s)]", event.getGuild().getName(), event.getGuild().getId()));
        } else if (GUILD_DELETE.equals(t)) {
            logger.info(String.format(t + " Event Exit From Guild[%s(%s)]", event.getGuild().getName(), event.getGuild().getId()));
            bot.delGuild(event.getGuild());
        }
        return event;
    }
}
