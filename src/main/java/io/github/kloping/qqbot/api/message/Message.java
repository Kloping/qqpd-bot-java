package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.Member;
import io.github.kloping.qqbot.api.interfaces.Sender;
import io.github.kloping.qqbot.api.message.audited.MessageAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import static io.github.kloping.qqbot.api.Channel.MAP;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>消息 id</td></tr> <tr><td>channel_id</td> <td>string</td> <td>子频道 id</td></tr> <tr><td>guild_id</td> <td>string</td> <td>频道 id</td></tr> <tr><td>content</td> <td>string</td> <td>消息内容</td></tr> <tr><td>timestamp</td> <td>ISO8601 timestamp</td> <td>消息创建时间</td></tr> <tr><td>edited_timestamp</td> <td>ISO8601 timestamp</td> <td>消息编辑时间</td></tr> <tr><td>mention_everyone</td> <td>bool</td> <td>是否是@全员消息</td></tr> <tr><td>author</td> <td><a href="/wiki/develop/api/openapi/user/model.html#user" class="">User</a> 对象</td> <td>消息创建者</td></tr> <tr><td>attachments</td> <td><a href="#messageattachment">MessageAttachment</a> 对象数组</td> <td>附件</td></tr> <tr><td>embeds</td> <td><a href="#messageembed">MessageEmbed</a> 对象数组</td> <td>embed</td></tr> <tr><td>mentions</td> <td><a href="/wiki/develop/api/openapi/user/model.html#user" class="">User</a> 对象数组</td> <td>消息中@的人</td></tr> <tr><td>member</td> <td><a href="/wiki/develop/api/openapi/member/model.html#member" class="">Member</a> 对象</td> <td>消息创建者的member信息</td></tr> <tr><td>ark</td> <td><a href="#messageark">MessageArk</a> ark消息对象</td> <td>ark消息</td></tr> <tr><td>seq</td> <td>int</td> <td>用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序。(目前只在消息事件中有值，<code>2022年8月1日</code> 后续废弃)</td></tr> <tr><td>seq_in_channel</td> <td>string</td> <td>子频道消息 seq，用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序</td></tr> <tr><td>message_reference</td> <td><a href="#messagereference">MessageReference</a> 对象</td> <td>引用消息对象</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Message implements Sender {
    private Author author;
    private String guild_id;
    private Member member;
    private String id;
    private String channel_id;
    private String content;
    private String timestamp;

    @Override
    public MessageAudited send(String text) {
        PreMessage msg = new PreMessage(text);
        msg.setMsg_id(Message.this.id);
        return Resource.messageBase.send(Message.this.channel_id, msg, MAP);
    }

    @Override
    public MessageAudited send(PreMessage msg) {
        return Resource.messageBase.send(Message.this.channel_id, msg, MAP);
    }
}