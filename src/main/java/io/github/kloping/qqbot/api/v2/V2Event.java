package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.api.event.Event;

/**
 * @author github.kloping
 */
public interface V2Event extends Event {
    /**
     * 所处环境 openid  可能是 user openid 或 group openid
      * @return
     */
    String getOpenId();
}
