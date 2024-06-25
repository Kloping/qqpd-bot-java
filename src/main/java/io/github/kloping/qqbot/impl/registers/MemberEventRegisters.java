package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.map.MapUtils;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.api.event.MemberUpdateEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Common;
import io.github.kloping.qqbot.entities.qqpd.MemberWithGuildID;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.BaseMemberRemoveEvent;
import io.github.kloping.qqbot.impl.BaseMemberUpdateEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class MemberEventRegisters implements Events.EventRegister {

    public static final String GUILD_MEMBER_ADD = "GUILD_MEMBER_ADD";
    public static final String GUILD_MEMBER_REMOVE = "GUILD_MEMBER_REMOVE";

    @AutoStandAfter
    private void r5(Events events) {
        events.register("GUILD_MEMBER_UPDATE", this).register(GUILD_MEMBER_ADD, this).register(GUILD_MEMBER_REMOVE, this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t,JSONObject mateData, RawMessage message) {
        MemberUpdateEvent event;
        MemberWithGuildID member;
        if (GUILD_MEMBER_REMOVE.equals(t)) {
            event = new BaseMemberRemoveEvent(mateData, bot);
            member = event.getMember();
        } else {
            event = new BaseMemberUpdateEvent(mateData, bot);
            member = event.getMember();
            event.getGuild().setMember(member);
        }
        if (GUILD_MEMBER_ADD.equals(t)) {
            MapUtils.append(Common.GUILD_MEMBER_TEMP, member.getGuildId(), member.getUser().getId(), member);
        } else if (GUILD_MEMBER_REMOVE.equals(t)) {
            Common.GUILD_MEMBER_TEMP.getOrDefault(event.getGuild().getId(), Common.EMPTY_MEMBER_MAP).remove(member.getUser().getId());
        }
        return event;
    }
}
