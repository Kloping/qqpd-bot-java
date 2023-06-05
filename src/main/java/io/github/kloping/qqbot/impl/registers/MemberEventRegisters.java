package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.qqbot.api.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.BaseMemberUpdateEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class MemberEventRegisters implements Events.EventRegister {
    @AutoStandAfter
    private void r5(Events events) {
        events.register("GUILD_MEMBER_UPDATE", this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t,JSONObject mateData, RawMessage message) {
        Event event = new BaseMemberUpdateEvent(mateData, bot);
        return event;
    }
}
