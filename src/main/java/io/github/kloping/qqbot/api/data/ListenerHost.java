package io.github.kloping.qqbot.api.data;

/**
 * @author github.kloping
 */
public abstract class ListenerHost {
    /**
     * 错误抛出处理
     *
     * @param e
     */
    public abstract void handleException(Throwable e);

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
}
