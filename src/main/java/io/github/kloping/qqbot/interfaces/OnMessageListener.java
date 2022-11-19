package io.github.kloping.qqbot.interfaces;


import io.github.kloping.qqbot.api.qqpd.message.Message;

/**
 * @author github.kloping
 */
public interface OnMessageListener {
    /**
     * on receive a message
     *
     * @param message
     */
    void onMessage(Message message);
}
