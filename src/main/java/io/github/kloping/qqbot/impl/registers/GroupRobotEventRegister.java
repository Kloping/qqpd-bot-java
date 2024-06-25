package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupAddRobotEvent;
import io.github.kloping.qqbot.impl.message.v2.BaseGroupDelRobotEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class GroupRobotEventRegister implements Events.EventRegister {

    public static final String GROUP_DEL_ROBOT = "GROUP_DEL_ROBOT";
    public static final String GROUP_ADD_ROBOT = "GROUP_ADD_ROBOT";

    @AutoStandAfter
    private void r0(Events events) {
        events.register(GROUP_ADD_ROBOT, this)
                .register(GROUP_DEL_ROBOT, this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        Event event = null;
        if (GROUP_ADD_ROBOT.equals(t)) {
            event = new BaseGroupAddRobotEvent(message, mateData, bot);
        } else if (GROUP_DEL_ROBOT.equals(t)) {
            event = new BaseGroupDelRobotEvent(message, mateData, bot);
        }
        return event;
    }

}
