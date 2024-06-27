package io.github.kloping.qqbot.http;

import io.github.kloping.spt.annotations.http.*;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.http.data.MutePack;
import org.jsoup.Connection;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface MemberBase {

    /**
     * 禁言指定
     *
     * @param guild
     * @param id
     * @param mute
     */
    @RequestPath(method = Connection.Method.PATCH, value = "/guilds/{guild_id}/members/{user_id}/mute")
    void muteOne(@PathValue("guild_id") String guild, @PathValue("user_id") String id, @RequestBody MutePack mute);

    /**
     * 禁言群体 全部 or 多个
     *
     * @param guild
     * @param mute
     */
    @RequestPath(method = Connection.Method.PATCH, value = "/guilds/{guild_id}/mute")
    void muteOne(@PathValue("guild_id") String guild, @RequestBody MutePack mute);
}
