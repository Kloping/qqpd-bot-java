package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupMemberEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/** 群成员变更事件基类。 */
public abstract class BaseGroupMemberEvent extends BaseGroupEvent implements GroupMemberEvent {
    protected BaseGroupMemberEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public Long getTimestamp() {
        return getMetadata().getLong("timestamp");
    }

    @Override
    public String getMemberOpenid() {
        return getMetadata().getString("member_openid");
    }

    @Override
    public String getUserOpenid() {
        return getMetadata().getString("user_openid");
    }
}
