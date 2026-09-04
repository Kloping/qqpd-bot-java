package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupJoinRequestEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinRequest;

/** 群成员入群申请事件实现。 */
public class BaseGroupJoinRequestEvent extends BaseGroupEvent implements GroupJoinRequestEvent {
    private final JoinRequest joinRequest;

    public BaseGroupJoinRequestEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
        this.joinRequest = jo.toJavaObject(JoinRequest.class);
        this.joinRequest.setGroup(getGroup());
    }

    @Override
    public JoinRequest getJoinRequest() {
        return joinRequest;
    }

    @Override
    public String getClassName() {
        return "GroupJoinRequestEvent";
    }
}
