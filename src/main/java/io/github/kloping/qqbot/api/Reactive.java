package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entities.qqpd.data.Emoji;

/**
 * 可被添加表情的
 *
 * @author github.kloping
 */
public interface Reactive {
    void addEmoji(Emoji emoji);

    void removeEmoji(Emoji emoji);
}
