package io.github.kloping.qqbot.api.v2;

/**
 * 机器人群聊事件
 *
 * @author github.kloping
 */
public interface V2Event {
    /**
     * 事件id
     *
     * @return
     */
    String getId();

    /**
     * 事情群聊openid
     *
     * @return
     */
    String getGroupOpenId();
}
