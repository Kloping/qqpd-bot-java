package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.PathValue;
import io.github.kloping.MySpringTool.annotations.http.Headers;
import io.github.kloping.MySpringTool.annotations.http.HttpClient;
import io.github.kloping.MySpringTool.annotations.http.PostPath;
import io.github.kloping.MySpringTool.annotations.http.RequestBody;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.qqpd.message.PreMessage;
import io.github.kloping.qqbot.api.qqpd.message.audited.MessageAudited;

import java.util.Map;

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
}
