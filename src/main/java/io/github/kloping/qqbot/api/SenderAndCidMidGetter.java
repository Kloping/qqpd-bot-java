package io.github.kloping.qqbot.api;

/**
 * @author github.kloping
 */
public interface SenderAndCidMidGetter extends Sender {
    /**
     * 获取 channel id
     *
     * @return
     */
    String getCid();

    /**
     * 获取 message id
     *
     * @return
     */
    default String getMid() {
        return null;
    }
}
