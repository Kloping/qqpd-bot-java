package io.github.kloping.qqbot.api.event;

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
