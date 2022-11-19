package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.PathValue;
import io.github.kloping.MySpringTool.annotations.http.*;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.qqpd.message.PreMessage;
import io.github.kloping.qqbot.api.qqpd.message.audited.MessageAudited;

import java.util.Map;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>content</td> <td>string</td> <td>选填，消息内容，文本内容，支持<a href="/wiki/develop/api/openapi/message/message_format.html" class="">内嵌格式</a></td></tr> <tr><td>embed</td> <td><a href="/wiki/develop/api/openapi/message/model.html#messageembed" class="">MessageEmbed</a></td> <td>选填，embed 消息，一种特殊的 ark，详情参考<a href="/wiki/develop/api/openapi/message/embed_message.html" class="">Embed消息</a></td></tr> <tr><td>ark</td> <td><a href="/wiki/develop/api/openapi/message/model.html#messageark" class="">MessageArk</a> ark消息对象</td> <td>选填，ark 消息</td></tr> <tr><td>message_reference</td> <td><a href="/wiki/develop/api/openapi/message/model.html#messagereference" class="">MessageReference</a> 引用消息对象</td> <td>选填，引用消息</td></tr> <tr><td>image</td> <td>string</td> <td>选填，图片url地址，平台会转存该图片，用于下发图片消息</td></tr> <tr><td>msg_id</td> <td>string</td> <td>选填，要回复的消息id(<a href="/wiki/develop/api/openapi/message/model.html#message" class="">Message</a>.id), 在 <a href="/wiki/develop/api/gateway/message.html" class="">AT_CREATE_MESSAGE</a> 事件中获取。</td></tr> <tr><td>markdown</td> <td><a href="/wiki/develop/api/openapi/message/model.html#messagemarkdown" class="">MessageMarkdown</a> markdown 消息对象</td> <td>选填，markdown 消息</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@HttpClient(Starter.NET_MAIN)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface MessageBase {
    /**
     * send
     *
     * @param cid
     * @param data
     * @return
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    @PostPath("/channels/{channel_id}/messages")
    String send(@PathValue("channel_id") String cid, @RequestData io.github.kloping.MySpringTool.entity.RequestData data);

    /**
     * send a message
     *
     * @param cid
     * @param body
     * @param headers
     * @return
     */
    @PostPath("/channels/{channel_id}/messages")
    MessageAudited send(@PathValue("channel_id") String cid, @RequestBody(type = RequestBody.type.json) PreMessage body, @Headers Map<String, String> headers);
}
