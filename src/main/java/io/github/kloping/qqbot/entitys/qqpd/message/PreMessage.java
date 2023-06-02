package io.github.kloping.qqbot.entitys.qqpd.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>content</td> <td>string</td> <td>选填，消息内容，文本内容，支持<a href="/wiki/develop/api/openapi/message/message_format.html" class="">内嵌格式</a></td></tr> <tr><td>embed</td> <td><a href="/wiki/develop/api/openapi/message/model.html#messageembed" class="">MessageEmbed</a></td> <td>选填，embed 消息，一种特殊的 ark，详情参考<a href="/wiki/develop/api/openapi/message/embed_message.html" class="">Embed消息</a></td></tr> <tr><td>ark</td> <td><a href="/wiki/develop/api/openapi/message/model.html#messageark" class="">MessageArk</a> ark消息对象</td> <td>选填，ark 消息</td></tr> <tr><td>message_reference</td> <td><a href="/wiki/develop/api/openapi/message/model.html#messagereference" class="">MessageReference</a> 引用消息对象</td> <td>选填，引用消息</td></tr> <tr><td>image</td> <td>string</td> <td>选填，图片url地址，平台会转存该图片，用于下发图片消息</td></tr> <tr><td>msg_id</td> <td>string</td> <td>选填，要回复的消息id(<a href="/wiki/develop/api/openapi/message/model.html#message" class="">Message</a>.id), 在 <a href="/wiki/develop/api/gateway/message.html" class="">AT_CREATE_MESSAGE</a> 事件中获取。</td></tr> <tr><td>event_id</td> <td>string</td> <td>选填，要回复的事件id, 在各事件对象中获取。</td></tr> <tr><td>markdown</td> <td><a href="/wiki/develop/api/openapi/message/model.html#messagemarkdown" class="">MessageMarkdown</a> markdown 消息对象</td> <td>选填，markdown 消息</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class PreMessage {
    private String content;
    private Object embed;
    private Object ark;
    private MessageReference messageReference;
    /**
     * 这里上传图片的网址必须是经过备案的域名下的文件 <br/> 否则将报错500 <br/>
     */
    private String image;
    private String msgId;
    private String eventId;
    private Object markdown;

    public PreMessage(String content) {
        this.content = content;
    }

    public PreMessage() {
    }
}
