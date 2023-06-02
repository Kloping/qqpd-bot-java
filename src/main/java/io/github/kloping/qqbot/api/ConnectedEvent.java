package io.github.kloping.qqbot.api;

/**
 * @author github.kloping
 */
public interface ConnectedEvent extends Event {
    /**
     * session id
     *
     * @return
     */
    String getSessionId();
}
