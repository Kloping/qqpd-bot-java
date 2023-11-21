package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.http.*;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.http.data.V2Result;

import java.util.Map;

/**
 * <table><tr><th colspan="2">基本</th></tr> <tr><td>HTTP URL</td> <td>/v2/groups/{group_openid}/messages</td></tr> <tr><td>HTTP Method</td> <td>POST</td></tr></table>
 * <hr>
 * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>group_openid</td> <td>string</td> <td>是</td> <td>群聊的 openid</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@HttpClient(Starter.NET_MAIN)
@Headers("io.github.kloping.qqbot.Start0.getV2Headers")
public interface UserBaseV2 {
    /**
     * 发送私聊消息
     * @param uid
     * @param body
     * @param headers
     * @return msg
     */
    @PostPath("/v2/users/{openid}/messages")
    V2Result send(@PathValue("openid") String uid, @RequestBody String body, @Headers Map<String, String> headers);

    /**
     * 发送私聊媒体
     * @param uid
     * @param body
     * @param headers
     * @return 文件id
     */
    @PostPath("/v2/users/{openid}/files")
    V2Result sendFile(@PathValue("openid") String uid, @RequestBody String body, @Headers Map<String, String> headers);
}
