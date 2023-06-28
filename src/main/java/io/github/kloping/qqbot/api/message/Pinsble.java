package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.entities.qqpd.PinsMessage;

/**
 * @author github.kloping
 */
public interface Pinsble {
    PinsMessage addPins();

    void deletePins();

    PinsMessage getPins();
}
