package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.C2cMsgEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import lombok.Getter;

/**
 * @author github.kloping
 */
@Getter
public class BaseC2cMsgEvent implements C2cMsgEvent {
    private String openid;
    private Long timestamp;
    private final JSONObject rawData;
    private final Bot bot;

    public BaseC2cMsgEvent(RawMessage message, JSONObject jo, Bot bot) {
        this.openid = jo.getString("openid");
        this.timestamp = jo.getLong("timestamp");
        this.rawData = jo;
        this.bot = bot;
    }

    @Override
    public JSONObject getMetadata() {
        return rawData;
    }

    @Override
    public String getId() {
        return openid;
    }

    @Override
    public String getOpenId() {
        return openid;
    }
}
