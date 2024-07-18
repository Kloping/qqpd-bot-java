package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.MemberUpdateEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.MemberWithGuildID;

/**
 * @author github.kloping
 */
public class BaseMemberUpdateEvent extends BaseGuildEvent implements MemberUpdateEvent {

    public BaseMemberUpdateEvent(JSONObject jo, Bot bot) {
        super();
        this.metadata = jo;
        this.bot = bot;
        this.member = jo.toJavaObject(MemberWithGuildID.class);
        if (member != null) member.setBot(bot);
        this.guild = getBot().getGuild(member.getGuildId());
        this.member.setGuild(guild);
        getGuild().setMember(member);
    }

    protected MemberWithGuildID member;

    @Override
    public MemberWithGuildID getMember() {
        return member;
    }

    @Override
    public String toString() {
        return String.format("member(%s) in %s info change", member.getNick(), guild.getName());
    }

    @Override
    public String getId() {
        return getMetadata().get("id").toString();
    }
}
