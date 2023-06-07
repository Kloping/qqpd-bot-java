package io.github.kloping.qqbot.entities;

import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.User;
import io.github.kloping.qqbot.http.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
@Entity
public class Bot {
    @AutoStand
    public GuildBase guildBase;

    @AutoStand
    public UserBase userBase;

    @AutoStand
    public ChannelBase channelBase;

    @AutoStand
    public DmsBase dmsBase;

    @AutoStand
    public MessageBase messageBase;

    private User user;

    private Map<String, Guild> guildMap = new HashMap<>();

    public synchronized Guild getGuild(String id) {
        if (guildMap.isEmpty()) {
            user = userBase.botInfo();
            for (Guild guild : guildBase.getGuilds()) {
                guildMap.put(guild.getId(), guild);
            }
        }
        return guildMap.get(id);
    }

    public Guild setGuild(Guild guild) {
        guildMap.put(guild.getId(), guild);
        return guildMap.get(guild.getId());
    }

    public Collection<Guild> guilds() {
        return guildMap.values();
    }

    public synchronized User getInfo() {
        if (user == null) {
            user = userBase.botInfo();
        }
        return user;
    }

    public String getId() {
        return getInfo().getId();
    }
}
