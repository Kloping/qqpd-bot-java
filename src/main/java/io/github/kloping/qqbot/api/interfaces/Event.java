package io.github.kloping.qqbot.api.interfaces;

import com.alibaba.fastjson.JSONObject;

/**
 * 所有事件的顶级接口
 *
 * @author github.kloping
 */
public interface Event {
    /**
     * 获得元数据
     *
     * @return
     */
    JSONObject getMetadata();
}
