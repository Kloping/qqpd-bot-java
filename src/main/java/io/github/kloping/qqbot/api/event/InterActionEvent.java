package io.github.kloping.qqbot.api.event;

import io.github.kloping.qqbot.api.Sender;
import io.github.kloping.qqbot.entities.qqpd.InterAction;

/**
 * @author github.kloping
 */
public interface InterActionEvent extends Event, Sender {
    /**
     * 0 频道场景，1 群聊场景，2 单聊场景
     *
     * @return
     */
    Integer getChatType();

    /**
     * 获得数据
     *
     * @return
     */
    InterAction getInterAction();

    /**
     * 响应
     * 0 成功 1 操作失败 2 操作频繁 3 重复操作 4 没有权限 5 仅管理员操作 <br>
     * 测试未知异常 暂不可用
     *
     * @param code
     */
    void response(int code);

    @Override
    default String getClassName() {
        return "InterActionEvent";
    }
}
