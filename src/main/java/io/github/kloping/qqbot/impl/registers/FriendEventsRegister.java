package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.message.v2.*;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;

/**
 * @author github.kloping
 */
@Entity
public class FriendEventsRegister implements Events.EventRegister {

    public static final String C2C_MESSAGE_CREATE = "C2C_MESSAGE_CREATE";
    public static final String FRIEND_ADD = "FRIEND_ADD";
    public static final String FRIEND_DEL = "FRIEND_DEL";
    public static final String C2C_MSG_RECEIVE = "C2C_MSG_RECEIVE";
    public static final String C2C_MSG_REJECT = "C2C_MSG_REJECT";

    @AutoStandAfter
    private void rN(Events events) {
        events.register(C2C_MESSAGE_CREATE, this).register(C2C_MSG_RECEIVE, this)
                .register(C2C_MSG_REJECT, this).register(FRIEND_ADD, this).register(FRIEND_DEL, this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        Event event = null;
        if (C2C_MESSAGE_CREATE.equals(t)) {
            event = new BaseFriendMessageEvent(message, mateData, bot);
        } else if (FRIEND_ADD.equals(t)) {
            event = new BaseFriendAdd(message, mateData, bot);
        } else if (FRIEND_DEL.equals(t)) {
            event = new BaseFriendDel(message, mateData, bot);
        } else if (C2C_MSG_RECEIVE.equals(t)) {
            event = new BaseC2cMsgReceiveEvent(message, mateData, bot);
        } else if (C2C_MSG_REJECT.equals(t)) {
            event = new BaseC2cMsgRejectEvent(message, mateData, bot);
        }
        return event;
    }
}
