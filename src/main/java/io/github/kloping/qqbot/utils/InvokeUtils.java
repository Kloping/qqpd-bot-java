package io.github.kloping.qqbot.utils;


import io.github.kloping.qqbot.impl.ListenerHost;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @author github.kloping
 */
public class InvokeUtils {
    public static Method[] getAllMethod(ListenerHost listenerHost) {
        List<Method> methods = new LinkedList<>();
        for (Method declaredMethod : listenerHost.getClass().getDeclaredMethods()) {
            if (declaredMethod.getDeclaredAnnotation(ListenerHost.EventReceiver.class) == null) continue;
            declaredMethod.setAccessible(true);
            methods.add(declaredMethod);
        }
        return methods.toArray(new Method[0]);
    }
}
