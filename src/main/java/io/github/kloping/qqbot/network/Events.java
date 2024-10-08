package io.github.kloping.qqbot.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.common.Public;
import io.github.kloping.map.MapUtils;
import io.github.kloping.qqbot.Starter;
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
import io.github.kloping.spt.interfaces.Logger;

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

    @AutoStand
    Logger logger;

    @AutoStand
    Bot bot;

    private void onEvent(String t, JSONObject obj) throws Exception {
        Class<? extends Event> c0 = null;
        RawMessage msg = null;
        msg = obj.toJavaObject(RawMessage.class);
        if (msg == null) {
            logger.waring(String.format("Unknown Pack(%s)", obj.toString()));
            return;
        }
        List<EventRegister> registers = id2reg.get(t);
        if (registers == null || registers.isEmpty()) {
            logger.waring(String.format("%s yet not registered", t));
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
                    Public.EXECUTOR_SERVICE.submit(() -> {
                        try {
                            method.invoke(l, event);
                        } catch (IllegalAccessException e) {
                            logger.error("EventReceiver The method parameter is set incorrectly");
                            logger.error(e.getMessage() + "\n\tat " + getExceptionLine(e));
                        } catch (InvocationTargetException e) {
                            if (l.handleException(e.getTargetException())) {
                                if (e.getTargetException() instanceof RequestException) {
                                    RequestException re = (RequestException) e.getTargetException();
                                    logger.error(String.format("%s: code(%s) %s at", re.getClass().getSimpleName(), re.getCode(), re.getData().getMessage()));
                                    for (StackTraceElement traceElement : re.getStackTrace()) {
                                        logger.error(String.format("\t%s.%s(%s:%s)", traceElement.getClassName(), traceElement.getMethodName(),
                                                traceElement.getFileName() == null ? "unknown" : traceElement.getFileName(), traceElement.getLineNumber()));
                                    }
                                } else {
                                    logger.error(getExceptionLine(e.getTargetException()));
                                }
                            }
                        } catch (Exception e) {
                            if (l.handleException(e))
                                logger.error(getExceptionLine(e));
                        }
                    });
                }
            }
            logger.info(String.format("Bot(%s) post(%s) from %s", bot.getInfo().getUsername(), event, event.getClassName()));
        }
    }

    private final Map<Method, ListenerHost> m2l = new HashMap<>();
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
