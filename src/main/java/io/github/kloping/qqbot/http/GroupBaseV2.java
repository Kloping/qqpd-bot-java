package io.github.kloping.qqbot.http;

import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.http.data.V2Result;
import io.github.kloping.spt.annotations.http.*;

import java.util.Map;

/**
 * <table><tr><th colspan="2">基本</th></tr> <tr><td>HTTP URL</td> <td>/v2/groups/{group_openid}/messages</td></tr> <tr><td>HTTP Method</td> <td>POST</td></tr></table>
 * <hr>
 * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>group_openid</td> <td>string</td> <td>是</td> <td>群聊的 openid</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@HttpClient(Starter.NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface GroupBaseV2 extends BaseV2{
    /**
     * 发送群聊消息
     * @param gid
     * @param body
     * @param headers
     * @return msg
     */
    @PostPath("/v2/groups/{group_openid}/messages")
    V2Result send(@PathValue("group_openid") String gid, @RequestBody(type = RequestBody.type.json) String body, @Headers Map<String, String> headers);

    /**
     * 发送群聊媒体
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>file_type</td> <td>int</td> <td>是</td> <td>媒体类型：1 图片，2 视频，3 语音，4 文件（暂不开放）<br>资源格式要求<br>图片：png/jpg，视频：mp4，语音：silk</td></tr> <tr><td>url</td> <td>string</td> <td>是</td> <td>需要发送媒体资源的url</td></tr> <tr><td>srv_send_msg</td> <td>bool</td> <td>是</td> <td>设置 true 会直接发送消息到目标端，且会占用<code>主动消息频次</code></td></tr> <tr><td>file_data</td> <td></td> <td>否</td> <td>【暂未支持】</td></tr></tbody></table>
     * @param gid
     * @param body
     * @param headers
     * @return 文件id
     */
    @PostPath("/v2/groups/{group_openid}/files")
    V2Result sendFile(@PathValue("group_openid") String gid, @RequestBody(type = io.github.kloping.spt.annotations.http.RequestBody.type.json) String body, @Headers Map<String, String> headers);
}
