package io.github.kloping.qqbot.interfaces;

import io.github.kloping.qqbot.entities.Pack;

/**
 * @author github.kloping
 */
public interface OnPackReceive {
    /**
     * on wss receive a pack
     *
     * @return If true is returned, interception continues
     * @param pack
     */
   boolean onReceive(Pack pack);
}
