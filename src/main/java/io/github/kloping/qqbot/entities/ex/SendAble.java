package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.qqpd.message.audited.MessageAudited;

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
    MessageAudited send(SenderAndCidMidGetter er);
}
