package io.github.kloping.qqbot.interfaces;

import io.github.kloping.qqbot.entitys.Pack;

/**
 * @author github.kloping
 */
public interface OnPackReceive {
    /**
     * on wss receive a pack
     *
     * @param pack
     */
    void onReceive(Pack pack);
}
