package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.ConnectedEvent;
import io.github.kloping.qqbot.entities.Bot;

/**
 * version show
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
    public Bot getBot() {
        return bot;
    }

    @Override
    public JSONObject getMetadata() {
        return metadata;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getClassName() {
        return ConnectedEvent.class.getSimpleName();
    }

    @Override
    public String toString() {
        return String.format("Bot(%s) Connected! By author kloping of bot-qqpd-java for version 1.5.1-L1", bot.getConfig().getAppid());
    }
}
