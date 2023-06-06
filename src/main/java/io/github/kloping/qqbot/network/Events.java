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
import io.github.kloping.qqbot.entities.Pack;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
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
        Public.EXECUTOR_SERVICE.submit(() -> {
            try {
                onEvent(t, jo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return false;
    }

    @AutoStand
    Starter.Config config;

    private final Set<String> ids = new HashSet<>();

    private void onEvent(String t, JSONObject obj) throws Exception {
        Class<? extends Event> c0 = null;
        RawMessage msg = obj.toJavaObject(RawMessage.class);
        if (msg != null) {
            if (msg.getId() != null && !msg.getId().isEmpty()) {
                if (ids.contains(msg.getId())) {
                    logger.waring(String.format("Filtering Duplicate messages(%s)", msg.getId()));
                    return;
                } else ids.add(msg.getId());
            }
        } else {
            logger.waring(String.format("Unknown Pack(%s)", obj.toString()));
            return;
        }
        EventRegister register = id2reg.get(t);
        if (register == null) {
            logger.waring(String.format("%s yet not registered", t));
            return;
        }
        Event event = register.handle(t, obj, msg);
        if (event == null) return;
        for (Method method : getM2L().keySet()) {
            ListenerHost l = getM2L().get(method);
            try {
                if (ObjectUtils.isSuperOrInterface(event.getClass(), method.getParameterTypes()[0])) {
                    method.invoke(l, event);
                }
            } catch (InvocationTargetException e) {
                e.getTargetException().printStackTrace();
                l.handleException(e.getTargetException());
            }
        }
        logger.info(String.format("%s post(%s)", event.getClass().getSimpleName(), event));
    }

    private final Map<Method, ListenerHost> m2l = new HashMap<>();

    private Map<Method, ListenerHost> getM2L() {
        if (m2l.isEmpty()) {
            synchronized (m2l) {
                for (ListenerHost listenerHost : config.getListenerHosts()) {
                    for (Method method : InvokeUtils.getAllMethod(listenerHost)) {
                        m2l.put(method, listenerHost);
                    }
                }
            }
        }
        return m2l;
    }

    public Map<String, EventRegister> id2reg = new HashMap<>();

    public EventRegister register(String id, EventRegister register) {
        return id2reg.put(id, register);
    }

    public interface EventRegister {
        Event handle(String t, JSONObject mateData, RawMessage message);
    }
}
