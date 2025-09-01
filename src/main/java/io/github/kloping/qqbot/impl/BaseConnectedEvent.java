package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.ConnectedEvent;
import io.github.kloping.qqbot.entities.Bot;

/**
 * version show
 *
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
        return String.format(FORMAT, bot.getConfig().getAppid());
    }

    @Override
    public String getId() {
        return getMetadata().get("id").toString();
    }

    public static final String VERSION = "1.5.2";
    public static final String PROJECT_NAME = "qqpd-bot-java";
    public static final String AUTHOR = "kloping";
    public static final String FORMAT = "Bot(%s) connected! By " + AUTHOR + " of " + PROJECT_NAME + " v" + VERSION;
    public static final String FORMAT_SERVER = "Bot(%s) webhook server started! By " + AUTHOR + " of " + PROJECT_NAME + " v" + VERSION;
}
