package io.github.kloping.qqbot.interfaces;


import io.github.kloping.qqbot.api.qqpd.message.Message;

/**
 * @author github.kloping
 */
public interface OnMessageDeleteListener {
    /**
     * 消息撤回时
     *
     * @param message
     */
    void onDelete(Message message);
}
