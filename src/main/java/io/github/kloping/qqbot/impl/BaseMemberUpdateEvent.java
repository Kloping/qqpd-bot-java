package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.MemberUpdateEvent;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.entitys.qqpd.MemberWithGuildID;

/**
 * @author github.kloping
 */
public class BaseMemberUpdateEvent extends BaseGuildEvent implements MemberUpdateEvent {
    public BaseMemberUpdateEvent(JSONObject jo, Bot bot) {
        super();
        this.metadata = jo;
        this.bot = bot;
        this.member = jo.toJavaObject(MemberWithGuildID.class);
        this.guild = getBot().getGuild(member.getGuildId());
        getGuild().setMember(member);
    }

    protected MemberWithGuildID member;

    @Override
    public MemberWithGuildID getMember() {
        return member;
    }

    @Override
    public String toString() {
        return String.format("member(%s) in %s info change", member.getNick(), guild.getName(), guild.getId());
    }
}
