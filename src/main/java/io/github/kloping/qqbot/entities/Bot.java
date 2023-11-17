package io.github.kloping.qqbot.entities;

import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.qqbot.Starter;
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

    @AutoStand
    public MemberBase memberBase;

    @AutoStand
    public GroupV2Base groupV2Base;

    @AutoStand
    public AuthV2Base authV2Base;

    @AutoStand
    Starter.Config config;

    private User user;

    private Map<String, Guild> guildMap = new HashMap<>();

    private void tryLoadGuilds() {
        if (guildMap.isEmpty()) {
            user = userBase.botInfo();
            for (Guild guild : guildBase.getGuilds()) {
                guild.setBot(this);
                guildMap.put(guild.getId(), guild);
            }
        }
    }

    public synchronized Guild getGuild(String id) {
        tryLoadGuilds();
        if (!guildMap.containsKey(id)) {
            Guild guild = guildBase.getGuild(id);
            if (guild != null) setGuild(guild);
        }
        return guildMap.get(id);
    }

    public Guild setGuild(Guild guild) {
        guildMap.put(guild.getId(), guild);
        return guildMap.get(guild.getId());
    }

    public Guild delGuild(Guild guild) {
        guildMap.remove(guild.getId());
        return guild;
    }

    public Collection<Guild> guilds() {
        tryLoadGuilds();
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

    public Starter.Config getConfig() {
        return config;
    }
}
