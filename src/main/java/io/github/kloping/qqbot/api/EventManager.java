package io.github.kloping.qqbot.api;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.object.ObjectUtils;
import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.data.ListenerHost;
import io.github.kloping.qqbot.api.data.message.*;
import io.github.kloping.qqbot.api.interfaces.Event;
import io.github.kloping.qqbot.api.interfaces.message.*;
import io.github.kloping.qqbot.api.qqpd.message.Message;
import io.github.kloping.qqbot.api.utils.InvokeUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.github.kloping.qqbot.Resource.logger;

/**
 * @author github.kloping
 */
public class EventManager {
    private static final Map<Method, ListenerHost> M2L = new HashMap<>();
    private static final Set<String> IDS = new HashSet<>();

    public static synchronized void onEvent(String t, JSONObject obj) {
        try {
            Class<? extends Event> c0 = null;
            Message msg = obj.toJavaObject(Message.class);
            if (msg != null) {
                if (msg.getId() != null && !msg.getId().isEmpty()) {
                    if (IDS.contains(msg.getId())) {
                        logger.waring(String.format("Filtering Duplicate messages(%s)", msg.getId()));
                        return;
                    } else {
                        (IDS).add(msg.getId());
                    }
                }
            } else {
                logger.waring(String.format("Unknown Pack(%s)", obj.toString()));
                return;
            }
            switch (t) {
                case "MESSAGE_CREATE":
                    if (msg.getMentions() != null && msg.getMentions().length > 0) {
                        c0 = MessageContainsAtEvent.class;
                    } else if (msg.getSrcGuildId() != null && !msg.getSrcGuildId().isEmpty()) {
                        c0 = MessageDirectReceiveEvent.class;
                    } else {
                        c0 = MessageChannelReceiveEvent.class;
                    }
                    break;
                case "AT_MESSAGE_CREATE":
                    c0 = MessageContainsAtEvent.class;
                    break;
                case "MESSAGE_DELETE":
                    c0 = MessageDeleteEvent.class;
                    break;
                case "DIRECT_MESSAGE_CREATE":
                    c0 = MessageDirectReceiveEvent.class;
                    break;
                default:
                    break;
            }
            if (c0 == null) return;
            if (M2L.isEmpty()) {
                synchronized (M2L) {
                    for (ListenerHost listenerHost : Resource.LISTENER_HOSTS) {
                        for (Method method : InvokeUtils.getAllMethod(c0, listenerHost)) {
                            M2L.put(method, listenerHost);
                        }
                    }
                }
            }
            logger.info(String.format("%s pre", c0.getSimpleName()));
            Class<? extends Event> finalC = c0;
            Object event = factory(msg, obj, finalC);
            M2L.forEach((m, l) -> {
                try {
                    if (ObjectUtils.isSuperOrInterface(event.getClass(), m.getParameterTypes()[0]))
                        m.invoke(l, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.getTargetException().printStackTrace();
                    l.handleException(e.getTargetException());
                }
            });
            logger.info(String.format("%s post(%s)", c0.getSimpleName(), event));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T extends Event> T factory(Message message, JSONObject jo, Class<T> cla) {
        Event event = null;
        if (cla == MessageReceiveEvent.class) {
            event = new BaseMessageReceiveEvent(message, jo);
        } else if (cla == MessageContainsAtEvent.class) {
            event = new BaseMessageContainsAtEvent(message, jo);
        } else if (cla == MessageDeleteEvent.class) {
            event = new BaseMessageDeleteEvent(message, jo);
        } else if (cla == MessageDirectReceiveEvent.class) {
            event = new BaseMessageDirectReceiveEvent(message, jo);
        } else if (cla == MessageChannelReceiveEvent.class) {
            event = new BaseMessageChannelReceiveEvent(message, jo);
        }
        return (T) event;
    }

}
