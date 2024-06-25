package io.github.kloping.qqbot.http;

import io.github.kloping.spt.annotations.http.*;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.ex.ChannelData;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.Roles;
import org.jsoup.Connection;

import java.util.Map;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_MAIN)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface GuildBase {
    /**
     * get guilds
     *
     * @return
     */
    @GetPath("/users/@me/guilds")
    Guild[] getGuilds();

    /**
     * get a guild
     *
     * @param gid
     * @return
     */
    @GetPath("/guilds/{gid}")
    Guild getGuild(@PathValue("gid") String gid);

    /**
     * get channels
     *
     * @param gid
     * @return
     */
    @GetPath("/guilds/{gid}/channels")
    Channel[] getChannels(@PathValue("gid") String gid);

    /**
     * get members
     *
     * @param gid
     * @param num
     * @return
     */
    @GetPath("/guilds/{guild_id}/members")
    Member[] getMembers(@PathValue("guild_id") String gid, @ParamName("limit") Integer num);

    /**
     * get member
     *
     * @param gid
     * @param userId
     * @return
     */
    @GetPath("/guilds/{guild_id}/members/{user_id}")
    Member getMember(@PathValue("guild_id") String gid, @PathValue("user_id") String userId);

    /**
     * get roles
     *
     * @param gid
     * @return
     */
    @GetPath("/guilds/{guild_id}/roles")
    Roles getRoles(@PathValue("guild_id") String gid);

    /**
     * 创建一个子频道
     *
     * @param headers
     * @param gid
     * @param data
     * @return
     */
    @PostPath("/guilds/{guild_id}/channels")
    Channel create(@Headers Map<String, String> headers, @PathValue("guild_id") String gid, @RequestBody ChannelData data);
}
