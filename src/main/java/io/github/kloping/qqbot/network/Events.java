package io.github.kloping.qqbot.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.event.ConnectedEvent;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.api.exc.RequestException;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.Pack;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.interfaces.OnPackReceive;
import io.github.kloping.qqbot.utils.InvokeUtils;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.spt.util.MapUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.github.kloping.spt.PartUtils.getExceptionLine;

/**
 * @author github.kloping
 */
@Entity
@Slf4j
public class Events implements OnPackReceive {
    public static final String EXTEND_ID = "raw-id";

    @AutoStandAfter
    public void r1(WssWorker wssWorker) {
        wssWorker.getOnPackReceives().add(this);
    }

    @Override
    public boolean onReceive(Pack pack) {
        String t = pack.getT();
        if (t == null) return false;
        JSONObject jo = JSON.parseObject(JSON.toJSONString(pack.getD()));
        jo.put(EXTEND_ID, pack.getId());
        config.getEventExecutor().submit(() -> {
            try {
                onEvent(t, jo);
            } catch (Exception e) {
                log.error("Event processing failed", e);
            }
        });
        return false;
    }

    @AutoStand
    Starter.Config config;

    @AutoStand
    Bot bot;

    private void onEvent(String t, JSONObject obj) throws Exception {
        Class<? extends Event> c0 = null;
        RawMessage msg = null;
        msg = obj.toJavaObject(RawMessage.class);
        if (msg == null) {
            log.warn("Unknown Pack({})", obj);
            return;
        }
        List<EventRegister> registers = id2reg.get(t);
        if (registers == null || registers.isEmpty()) {
            log.warn("{} yet not registered", t);
            return;
        }
        msg.setBot(bot);
        for (EventRegister register : registers) {
            Event event = register.handle(t, obj, msg);
            if (event == null) return;
            for (Method method : getM2L().keySet()) {
                ListenerHost l = getM2L().get(method);
                if (method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                    invokeBefore(l, event, method, Events.this);
                    config.getEventExecutor().submit(() -> {
                        try {
                            method.invoke(l, event);
                        } catch (IllegalAccessException e) {
                            log.error("EventReceiver method parameter is set incorrectly", e);
                        } catch (InvocationTargetException e) {
                            if (l.handleException(e.getTargetException())) {
                                if (e.getTargetException() instanceof RequestException) {
                                    RequestException re = (RequestException) e.getTargetException();
                                    log.error("{}: code({}) {} at", re.getClass().getSimpleName(), re.getCode(), re.getData().getMessage());
                                    for (StackTraceElement traceElement : re.getStackTrace()) {
                                        log.error(String.format("\t%s.%s(%s:%s)", traceElement.getClassName(), traceElement.getMethodName(),
                                                traceElement.getFileName() == null ? "unknown" : traceElement.getFileName(), traceElement.getLineNumber()));
                                    }
                                } else {
                                    log.error("Event receiver invocation failed", e.getTargetException());
                                }
                            }
                        } catch (Exception e) {
                            if (l.handleException(e))
                                log.error("Event receiver invocation failed", e);
                        }
                    });
                }
            }
            if (!(event instanceof ConnectedEvent && connectedEventLogged)) {
                log.info("Bot({}) post({}) from {}", bot.getInfo().getUsername(), event, event.getClassName());
                if (event instanceof ConnectedEvent) connectedEventLogged = true;
            }
        }
    }

    private final Map<Method, ListenerHost> m2l = new HashMap<>();
    private volatile boolean connectedEventLogged;
    private int cap = 0;

    private Map<Method, ListenerHost> getM2L() {
        if (m2l.isEmpty() || cap != config.getListenerHosts().size()) {
            synchronized (m2l) {
                cap = 0;
                for (ListenerHost listenerHost : config.getListenerHosts()) {
                    for (Method method : InvokeUtils.getAllMethod(listenerHost)) {
                        m2l.put(method, listenerHost);
                    }
                    cap++;
                }
            }
        }
        return m2l;
    }

    public Map<String, List<EventRegister>> id2reg = new HashMap<>();

    public Events register(String id, EventRegister register) {
        MapUtils.append(id2reg, id, register, LinkedList.class);
        return this;
    }

    public interface EventRegister {
        Event handle(String t, JSONObject mateData, RawMessage message);
    }

    private void invokeBefore(ListenerHost l, Event event, Method method, Events events) {
        if (method.isAnnotationPresent(ListenerHost.Filter.class)) {
            if (event instanceof MessageEvent) {
                ListenerHost.Filter filter = method.getAnnotation(ListenerHost.Filter.class);
                ((MessageEvent) event).setFilter(filter.exclusions());
            }
        }
    }
}
