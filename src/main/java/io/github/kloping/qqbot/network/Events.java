package io.github.kloping.qqbot.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.common.Public;
import io.github.kloping.object.ObjectUtils;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Event;
import io.github.kloping.qqbot.entitys.Pack;
import io.github.kloping.qqbot.entitys.qqpd.message.Message;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.interfaces.OnPackReceive;
import io.github.kloping.qqbot.utils.InvokeUtils;

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
@Entity
public class Events implements OnPackReceive {
    @AutoStandAfter
    public void r1(WssWorker wssWorker) {
        wssWorker.getOnPackReceives().add(this);
    }

    @Override
    public boolean onReceive(Pack pack) {
        String t = pack.getT();
        if (t == null) return false;
        JSONObject jo = JSON.parseObject(JSON.toJSONString(pack.getD()));
        Public.EXECUTOR_SERVICE.submit(() -> onEvent(t, jo));
        Message m = jo.toJavaObject(Message.class);

        return false;
    }

    @AutoStand
    Starter.Config config;

    private final Map<Method, ListenerHost> M2L = new HashMap<>();
    private final Set<String> IDS = new HashSet<>();

    private void onEvent(String t, JSONObject obj) {
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
            EventRegister register = id2reg.get(t);
            if (register == null) return;
            Event event = register.handle(obj, msg);
            if (M2L.isEmpty()) {
                synchronized (M2L) {
                    for (ListenerHost listenerHost : config.getListenerHosts()) {
                        for (Method method : InvokeUtils.getAllMethod(c0, listenerHost)) {
                            M2L.put(method, listenerHost);
                        }
                    }
                }
            }
            M2L.forEach((m, l) -> {
                try {
                    if (ObjectUtils.isSuperOrInterface(event.getClass(), m.getParameterTypes()[0])) m.invoke(l, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.getTargetException().printStackTrace();
                    l.handleException(e.getTargetException());
                }
            });
            logger.info(String.format("%s post(%s)", event.getClass().getSimpleName(), event));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, EventRegister> id2reg = new HashMap<>();

    public EventRegister register(String id, EventRegister register) {
        return id2reg.put(id, register);
    }

    public interface EventRegister {
        Event handle(JSONObject mateData, Message message);
    }
}
