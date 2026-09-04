package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.GroupMsgRejectEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/** 群消息接收通知关闭事件实现。 */
public class BaseGroupMsgRejectEvent extends BaseGroupOpRobotEvent implements GroupMsgRejectEvent {
    public BaseGroupMsgRejectEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public String getClassName() {
        return "GroupMsgRejectEvent";
    }
}
