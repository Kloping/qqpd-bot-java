package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.PathValue;
import io.github.kloping.MySpringTool.annotations.http.GetPath;
import io.github.kloping.MySpringTool.annotations.http.Headers;
import io.github.kloping.MySpringTool.annotations.http.HttpClient;
import io.github.kloping.MySpringTool.annotations.http.ParamName;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.qqpd.Channel;
import io.github.kloping.qqbot.api.qqpd.Guild;
import io.github.kloping.qqbot.api.qqpd.Member;
import io.github.kloping.qqbot.api.qqpd.Roles;

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
    @GetPath("/guilds/")
    Guild getGuild(@PathValue String gid);

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
}
