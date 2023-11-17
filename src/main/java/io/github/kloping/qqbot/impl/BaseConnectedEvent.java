package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.ConnectedEvent;
import io.github.kloping.qqbot.entities.Bot;

/**
 * @author github.kloping
 */
public class BaseConnectedEvent implements ConnectedEvent {
    protected JSONObject metadata;
    protected Bot bot;
    private String sessionId;

    public BaseConnectedEvent(JSONObject metadata, Bot bot, String sessionId) {
        this.metadata = metadata;
        this.bot = bot;
        this.sessionId = sessionId;
    }

    @Override
    public JSONObject getMetadata() {
        return metadata;
    }

    @Override
    public Bot getBot() {
        return bot;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String toString() {
        return String.format("Bot(%s) Started! By author kloping of bot-qqpd-java for version 1.5.0-L6", bot.getInfo().getUsername());
    }
}
