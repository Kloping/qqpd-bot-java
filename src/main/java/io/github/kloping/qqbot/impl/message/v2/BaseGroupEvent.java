package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
public abstract class BaseGroupEvent implements GroupEvent {

    private final Group group;
    private final JSONObject rawData;
    private final Bot bot;

    public BaseGroupEvent(RawMessage message, JSONObject jo, Bot bot) {
        this.rawData = jo;
        this.bot = bot;
        this.group = new Group(getMetadata());
    }

    @Override
    public JSONObject getMetadata() {
        return rawData;
    }

    @Override
    public Bot getBot() {
        return bot;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public String getGroupId() {
        return getGroup().getOpenid();
    }

    @Override
    public String getId() {
        return rawData.getString(Events.EXTEND_ID);
    }
}
