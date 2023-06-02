package io.github.kloping.qqbot.entitys.qqpd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author github.kloping
 */
public class Common {
    /**
     * key
     * guild id
     * value
     * key: memberId value:
     */
    public static final Map<String, Map<String, Member>> GUILD_MEMBER_TEMP = new ConcurrentHashMap<>();
    public static final Map<String, Map<String, Channel>> GUILD_CHANNEL_TEMP = new ConcurrentHashMap<>();
}
