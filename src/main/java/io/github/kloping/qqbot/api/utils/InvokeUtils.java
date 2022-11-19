package io.github.kloping.qqbot.api.utils;

import io.github.kloping.object.ObjectUtils;
import io.github.kloping.qqbot.api.data.EventReceiver;
import io.github.kloping.qqbot.api.data.ListenerHost;
import io.github.kloping.qqbot.api.interfaces.Event;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @author github.kloping
 */
public class InvokeUtils {
    public static <T extends Event> Method[] getAllMethod(Class<T> cla, ListenerHost listenerHost) {
        List<Method> methods = new LinkedList<>();
        for (Method declaredMethod : listenerHost.getClass().getDeclaredMethods()) {
            if (declaredMethod.getDeclaredAnnotation(EventReceiver.class) == null) continue;
            declaredMethod.setAccessible(true);
            methods.add(declaredMethod);
        }
        return methods.toArray(new Method[0]);
    }
}
