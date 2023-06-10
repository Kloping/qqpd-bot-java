package io.github.kloping.qqbot.entities.qqpd.message;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.DeleteAble;
import io.github.kloping.qqbot.api.Reactive;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.User;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.impl.MessagePacket;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>消息 id</td></tr> <tr><td>channel_id</td> <td>string</td> <td>子频道 id</td></tr> <tr><td>guild_id</td> <td>string</td> <td>频道 id</td></tr> <tr><td>content</td> <td>string</td> <td>消息内容</td></tr> <tr><td>timestamp</td> <td>ISO8601 timestamp</td> <td>消息创建时间</td></tr> <tr><td>edited_timestamp</td> <td>ISO8601 timestamp</td> <td>消息编辑时间</td></tr> <tr><td>mention_everyone</td> <td>bool</td> <td>是否是@全员消息</td></tr> <tr><td>author</td> <td><a href="/wiki/develop/api/openapi/user/model.html#user" class="">User</a> 对象</td> <td>消息创建者</td></tr> <tr><td>attachments</td> <td><a href="#messageattachment">MessageAttachment</a> 对象数组</td> <td>附件</td></tr> <tr><td>embeds</td> <td><a href="#messageembed">MessageEmbed</a> 对象数组</td> <td>embed</td></tr> <tr><td>mentions</td> <td><a href="/wiki/develop/api/openapi/user/model.html#user" class="">User</a> 对象数组</td> <td>消息中@的人</td></tr> <tr><td>member</td> <td><a href="/wiki/develop/api/openapi/member/model.html#member" class="">Member</a> 对象</td> <td>消息创建者的member信息</td></tr> <tr><td>ark</td> <td><a href="#messageark">MessageArk</a> ark消息对象</td> <td>ark消息</td></tr> <tr><td>seq</td> <td>int</td> <td>用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序。(目前只在消息事件中有值，<code>2022年8月1日</code> 后续废弃)</td></tr> <tr><td>seq_in_channel</td> <td>string</td> <td>子频道消息 seq，用于消息间的排序，seq 在同一子频道中按从先到后的顺序递增，不同的子频道之间消息无法排序</td></tr> <tr><td>message_reference</td> <td><a href="#messagereference">MessageReference</a> 对象</td> <td>引用消息对象</td></tr> <tr><td>src_guild_id</td> <td>string</td> <td>用于私信场景下识别真实的来源频道id</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class RawMessage implements SenderAndCidMidGetter, DeleteAble, Reactive {
    private String id;
    private String channelId;
    private String guildId;
    private String content;
    private String timestamp;
    private String editedTimestamp;
    private Boolean mentionEveryone;
    private User author;
    private MessageAttachment[] attachments;
    private Object embed;
    private User[] mentions;
    private Member member;
    private Object ark;
    private Integer seq;
    private String seqInChannel;
    private MessageReference messageReference;
    private String srcGuildId;

    @Override
    public ActionResult send(String text, RawMessage message) {
        return send(new MessagePacket().setContent(text).setReplyId(message.id));
    }

    @Override
    public ActionResult send(String text) {
        return send(new MessagePacket().setContent(text));
    }

    @Override
    public ActionResult send(MessagePacket packet) {
        RawPreMessage msg = new RawPreMessage();
        msg.setMsgId(RawMessage.this.id);
        BaseUtils.packet2pre(packet, msg);
        return bot.messageBase.send(RawMessage.this.channelId, msg, SEND_MESSAGE_HEADERS);
    }

    @Override
    public ActionResult send(RawPreMessage msg) {
        return bot.messageBase.send(RawMessage.this.channelId, msg, SEND_MESSAGE_HEADERS);
    }

    @Override
    public Object delete() {
        return bot.messageBase.delete(this.channelId, this.id, false);
    }

    public String getContent() {
        if (attachments != null)
            for (MessageAttachment attachment : attachments) {
                if (attachment.getUrl().contains("pic")) {
                    if (content == null) content = "";
                    content = content + "[图片]";
                }
            }
        return content;
    }

    @Override
    public ActionResult send(SendAble msg) {
        return msg.send(this);
    }

    @Override
    public String getCid() {
        return getChannelId();
    }

    @Override
    public String getMid() {
        return getId();
    }

    @Override
    public void addEmoji(Emoji emoji) {
        bot.channelBase.addEmoji(getChannelId(), getMid(), emoji.getType(), emoji.getId().toString());
    }

    @Override
    public void removeEmoji(Emoji emoji) {
        bot.channelBase.removeEmoji(getChannelId(), getMid(), emoji.getType(), emoji.getId().toString());
    }

    @JSONField(serialize = false, deserialize = false)
    private Bot bot;

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}