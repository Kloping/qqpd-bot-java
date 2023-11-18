package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.http.data.Result;

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
    Result send(SenderAndCidMidGetter er);
}
