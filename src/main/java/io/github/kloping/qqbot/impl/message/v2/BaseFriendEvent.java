package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.FriendEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Friend;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
public abstract class BaseFriendEvent implements FriendEvent {

    private final Friend friend;
    private final JSONObject rawData;
    private final Bot bot;

    public BaseFriendEvent(RawMessage message, JSONObject jo, Bot bot) {
        this.rawData = jo;
        this.bot = bot;
        this.friend = new Friend(getMetadata());
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
    public Friend getFriend() {
        return friend;
    }

    @Override
    public String getOpenId() {
        return getFriend().getId();
    }

    @Override
    public String getId() {
        return rawData.getString(Events.EXTEND_ID);
    }
}
