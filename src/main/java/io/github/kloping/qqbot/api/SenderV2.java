package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.http.BaseV2;

/**
 * @author github.kloping
 */
public interface SenderV2 extends Sender, BotContent {
    BaseV2 getV2();

    default Integer getMsgSeq() {
        return 1;
    }
}
