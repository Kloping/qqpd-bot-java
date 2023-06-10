package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.GuildEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Guild;

/**
 * @author github.kloping
 */
public abstract class BaseGuildEvent implements GuildEvent {
    protected JSONObject metadata;
    protected Guild guild;

    protected Bot bot;

    public BaseGuildEvent() {
    }

    public BaseGuildEvent(JSONObject jo, Bot bot) {
        this.metadata = jo;
        this.bot = bot;
        this.guild = jo.toJavaObject(Guild.class);
        getBot().setGuild(guild);
    }

    @Override
    public JSONObject getMetadata() {
        return metadata;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public Bot getBot() {
        return bot;
    }
}
