package io.github.kloping.qqbot.entitys;

import io.github.kloping.qqbot.api.Guild;
import io.github.kloping.qqbot.api.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
public class Bot {
    private User user;

    private Map<String, Guild> guildMap = new HashMap<>();

    public Bot(User user, Map<String, Guild> guildMap) {
        this.user = user;
        this.guildMap = guildMap;
    }

    public Guild getGuild(String id) {
        return guildMap.get(id);
    }

    public Collection<Guild> guilds() {
        return guildMap.values();
    }

    public User getInfo() {
        return user;
    }
}
