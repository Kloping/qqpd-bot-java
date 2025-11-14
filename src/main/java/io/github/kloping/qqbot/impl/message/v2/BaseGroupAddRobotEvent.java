package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupOpRobotEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * @author github.kloping
 */
public class BaseGroupAddRobotEvent extends BaseGroupOpRobotEvent implements GroupOpRobotEvent {
    public BaseGroupAddRobotEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public String getClassName() {
        return "GroupAddRobotEvent";
    }
}
