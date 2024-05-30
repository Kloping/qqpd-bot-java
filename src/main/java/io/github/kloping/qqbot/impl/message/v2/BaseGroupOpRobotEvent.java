package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupOpRobotEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * @author github.kloping
 */
public abstract class BaseGroupOpRobotEvent extends BaseGroupEvent implements GroupOpRobotEvent {
    public BaseGroupOpRobotEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public String getOpMemberOpenid() {
        return getMetadata().getString("op_member_openid");
    }

    @Override
    public Long getTimestamp() {
        return getMetadata().getLong("timestamp");
    }

    @Override
    public String toString() {
        return String.format("op robot in %s from %s", getOpMemberOpenid(), getGroupId());
    }
}
