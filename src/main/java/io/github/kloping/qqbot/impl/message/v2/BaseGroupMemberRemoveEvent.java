package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupMemberRemoveEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/** 群成员退出或被移出事件实现。 */
public class BaseGroupMemberRemoveEvent extends BaseGroupMemberEvent implements GroupMemberRemoveEvent {
    public BaseGroupMemberRemoveEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public String getClassName() {
        return "GroupMemberRemoveEvent";
    }
}
