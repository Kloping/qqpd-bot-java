package io.github.kloping.qqbot.interfaces;

import io.github.kloping.qqbot.api.message.Message;

/**
 * 其他时间监听
 *
 * @author github.kloping
 */
public interface OnOtherEventListener {
    /**
     * 时间发生时
     *
     * @param eventName
     * @param message
     */
    void onEvent(String eventName, Message message);
}
