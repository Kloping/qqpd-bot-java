package io.github.kloping.qqbot.interfaces;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.Message;

/**
 * 其他事件监听
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
    void onEvent(String eventName, JSONObject obj);
}
