package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupJoinRequestEvent;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupMemberAddEvent;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupMemberRemoveEvent;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupMessageEvent;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupMsgReceiveEvent;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupMsgRejectEvent;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;

/**
 * @author github.kloping
 */
@Entity
public class GroupEventsRegister implements Events.EventRegister {
    public static final String GROUP_AT_MESSAGE_CREATE = "GROUP_AT_MESSAGE_CREATE";
    public static final String GROUP_MESSAGE_CREATE = "GROUP_MESSAGE_CREATE";
    public static final String GROUP_MSG_RECEIVE = "GROUP_MSG_RECEIVE";
    public static final String GROUP_MSG_REJECT = "GROUP_MSG_REJECT";
    public static final String GROUP_MEMBER_ADD = "GROUP_MEMBER_ADD";
    public static final String GROUP_MEMBER_REMOVE = "GROUP_MEMBER_REMOVE";
    public static final String GROUP_JOIN_REQUEST = "GROUP_JOIN_REQUEST";

    @AutoStandAfter
    private void r4(Events events) {
        events.register(GROUP_AT_MESSAGE_CREATE, this);
        events.register(GROUP_MESSAGE_CREATE, this);
        events.register(GROUP_MSG_RECEIVE, this);
        events.register(GROUP_MSG_REJECT, this);
        events.register(GROUP_MEMBER_ADD, this);
        events.register(GROUP_MEMBER_REMOVE, this);
        events.register(GROUP_JOIN_REQUEST, this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        Event event = null;
        if (GROUP_AT_MESSAGE_CREATE.equals(t)) {
            event = new BaseGroupMessageEvent(message, mateData, bot);
        } else if (GROUP_MESSAGE_CREATE.equals(t)) {
            event = new BaseGroupMessageEvent(message, mateData, bot);
        } else if (GROUP_MSG_RECEIVE.equals(t)) {
            event = new BaseGroupMsgReceiveEvent(message, mateData, bot);
        } else if (GROUP_MSG_REJECT.equals(t)) {
            event = new BaseGroupMsgRejectEvent(message, mateData, bot);
        } else if (GROUP_MEMBER_ADD.equals(t)) {
            event = new BaseGroupMemberAddEvent(message, mateData, bot);
        } else if (GROUP_MEMBER_REMOVE.equals(t)) {
            event = new BaseGroupMemberRemoveEvent(message, mateData, bot);
        } else if (GROUP_JOIN_REQUEST.equals(t)) {
            event = new BaseGroupJoinRequestEvent(message, mateData, bot);
        } else {
        }
        return event;
    }
}
