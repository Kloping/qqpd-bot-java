package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.entities.qqpd.Dms;

/**
 * 私信会话创建接口
 *
 * @author github.kloping
 */
public interface SessionCreator {
    /**
     * 私信会话创建
     *
     * @param uid
     * @return
     */
    Dms create(String uid);
}
