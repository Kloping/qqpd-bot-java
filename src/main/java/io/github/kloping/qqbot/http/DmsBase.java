package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.PathValue;
import io.github.kloping.MySpringTool.annotations.http.*;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entitys.qqpd.Dms;
import io.github.kloping.qqbot.entitys.qqpd.DmsRequest;
import io.github.kloping.qqbot.entitys.qqpd.message.PreMessage;
import io.github.kloping.qqbot.entitys.qqpd.message.audited.MessageAudited;

import java.util.Map;

import static org.jsoup.Connection.Method.DELETE;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_MAIN)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface DmsBase {
    /**
     * send a  Direct message
     *
     * @param gid
     * @param body
     * @param headers
     * @return
     */
    @PostPath("/dms/{guild_id}/messages")
    MessageAudited send(@PathValue("guild_id") String gid, @RequestBody(type = RequestBody.type.json) PreMessage body, @Headers Map<String, String> headers);

    /**
     * create The session
     *
     * @param request
     * @param header0
     * @return
     */
    @PostPath("/users/@me/dms")
    Dms create(@RequestBody(type = RequestBody.type.json) DmsRequest request, @Headers Map<String, String> header0);


    /**
     * 撤回一条消息
     *
     * @param gid
     * @param mid
     * @param hidetip
     * @return
     */
    @RequestPath(method = DELETE, value = "/dms/{guild_id}/messages/{message_id}")
    Object delete(@PathValue("guild_id") String gid,
                  @PathValue("message_id") String mid, @ParamName("hidetip") Boolean hidetip);
}
