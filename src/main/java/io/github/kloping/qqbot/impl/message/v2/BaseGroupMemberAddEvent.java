package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupMemberAddEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/** 群成员加入事件实现。 */
public class BaseGroupMemberAddEvent extends BaseGroupMemberEvent implements GroupMemberAddEvent {
    public BaseGroupMemberAddEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public String getClassName() {
        return "GroupMemberAddEvent";
    }
}
