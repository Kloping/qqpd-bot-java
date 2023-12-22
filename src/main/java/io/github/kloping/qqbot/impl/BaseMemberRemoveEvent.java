package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.MemberUpdateEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.MemberWithGuildID;

/**
 * @author github.kloping
 */
public class BaseMemberRemoveEvent extends BaseGuildEvent implements MemberUpdateEvent {

    public BaseMemberRemoveEvent(JSONObject jo, Bot bot) {
        super();
        this.metadata = jo;
        this.bot = bot;
        this.member = jo.toJavaObject(MemberWithGuildID.class);
        this.guild = getBot().getGuild(member.getGuildId());
        this.member.setGuild(guild);
        if (member != null) member.setBot(bot);
    }

    protected MemberWithGuildID member;

    @Override
    public MemberWithGuildID getMember() {
        return member;
    }

    @Override
    public String toString() {
        return String.format("member(%s) remove from %s", member.getNick(), guild.getName());
    }
}
