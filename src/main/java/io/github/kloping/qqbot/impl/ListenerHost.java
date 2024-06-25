package io.github.kloping.qqbot.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author github.kloping
 */
public abstract class ListenerHost {
    /**
     * 错误抛出处理
     *
     * @param e
     * @return true 继续日志打印报错
     */
    public boolean handleException(Throwable e) {
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    /**
     * @author github.kloping
     */
    @Target(ElementType.METHOD)
    @java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
    public @interface EventReceiver {
    }

    /**
     * 过滤不需要的消息类型 且必须与 {@link EventReceiver} 一起使用
     * <br>
     * <code>
     * //例如
     * <br>{@code @EventReceiver}
     * <br>{@code @Filter(exclusions = {At.class})}
     * </code>
     */
    @Target(ElementType.METHOD)
    @java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
    public @interface Filter {
        Class<?>[] exclusions();
    }
}
