package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupMsgReceiveEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/** 群消息接收通知开启事件实现。 */
public class BaseGroupMsgReceiveEvent extends BaseGroupOpRobotEvent implements GroupMsgReceiveEvent {
    public BaseGroupMsgReceiveEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public String getClassName() {
        return "GroupMsgReceiveEvent";
    }
}
