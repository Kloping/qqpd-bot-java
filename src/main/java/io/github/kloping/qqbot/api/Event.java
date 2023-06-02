package io.github.kloping.qqbot.api;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.entitys.Bot;

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

    /**
     * 事件所在bot
     *
     * @return
     */
    Bot getBot();
}
