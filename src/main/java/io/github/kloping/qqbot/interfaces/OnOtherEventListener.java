package io.github.kloping.qqbot.interfaces;

import com.alibaba.fastjson.JSONObject;

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
     * @param obj
     */
    void onEvent(String eventName, JSONObject obj);
}
