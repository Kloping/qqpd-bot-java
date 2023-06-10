package io.github.kloping.qqbot.api.event;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.entities.Bot;

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
