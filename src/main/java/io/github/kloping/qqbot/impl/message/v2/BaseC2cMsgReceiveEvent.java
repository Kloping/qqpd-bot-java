package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.C2cMsgEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * @author github.kloping
 */
public class BaseC2cMsgReceiveEvent extends BaseC2cMsgEvent implements C2cMsgEvent {
    public BaseC2cMsgReceiveEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public String toString() {
        return "C2cMsgReceiveEvent FID:" + getOpenId();
    }

    @Override
    public String getClassName() {
        return "C2cMsgReceiveEvent";
    }
}
