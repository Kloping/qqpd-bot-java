package io.github.kloping.qqbot.api.v2;

/**
 * 单聊消息接收 开启/关闭
 *
 * @author github.kloping
 */
public interface C2cMsgEvent extends V2Event {
    String getOpenId();

    Long getTimestamp();
}
