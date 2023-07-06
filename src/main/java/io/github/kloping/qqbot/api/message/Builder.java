package io.github.kloping.qqbot.api.message;

/**
 * @author github.kloping
 */
public interface Builder<R, T> {
    Builder append(R r);

    T build();
}
