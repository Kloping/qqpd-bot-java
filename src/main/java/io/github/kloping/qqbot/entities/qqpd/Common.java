package io.github.kloping.qqbot.entities.qqpd;

import java.util.HashMap;
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
    public static final Map<String, Member> EMPTY_MEMBER_MAP = new HashMap<>();
    public static final Map<String, Channel> EMPTY_CHANNEL_MAP = new HashMap<>();
}
