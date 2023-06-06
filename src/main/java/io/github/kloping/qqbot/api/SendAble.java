package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.http.data.ActionResult;

/**
 * @author github.kloping
 */
public interface SendAble {
    /**
     * 所有可发送的
     *
     * @param er
     * @return
     */
    ActionResult send(SenderAndCidMidGetter er);
}
