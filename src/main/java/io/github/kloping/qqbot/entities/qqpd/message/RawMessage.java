package io.github.kloping.qqbot.entities.qqpd.message;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.api.*;
import io.github.kloping.qqbot.api.message.Pinsble;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.PinsMessage;
import io.github.kloping.qqbot.entities.qqpd.User;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.http.BaseV2;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import io.github.kloping.qqbot.impl.MessagePacket;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.*;
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
public class RawMessage implements SenderAndCidMidGetter, DeleteAble, Reactive, Pinsble, SenderV2 {
    private String id;
    private String channelId;
    private String guildId;
    @Getter
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

    @JSONField(alternateNames = "group_openid")
    private String srcGuildId;

    @Setter
    @JSONField(serialize = false, deserialize = false)
    private EnvType envType = EnvType.GUILD;

    @Override
    public Result send(String text, RawMessage message) {
        if (envType == EnvType.GUILD) return send(new MessagePacket().setContent(text).setReplyId(message.id));
        else if (envType == EnvType.GROUP || envType == EnvType.GROUP_USER) {
            V2MsgData data = new V2MsgData().setMsg_id(message.getId()).setContent(text).setMsg_seq(getMsgSeq());
            return new Result<V2Result>(getV2().send(message.getSrcGuildId(), data.toString(), SEND_MESSAGE_HEADERS));
        } else return null;
    }

    @Override
    public Result send(String text) {
        return send(text, this);
    }

    public static boolean imagePrepare(Image msg, Bot bot) {
        try {
            if (Judge.isEmpty(msg.getUrl())) {
                if (msg.getBytes() != null) if (bot.getConfig().getInterceptor0() != null) {
                    String url = bot.getConfig().getInterceptor0().upload(msg.getBytes());
                    if (Judge.isNotEmpty(url)) {
                        msg.setUrl(url);
                    } else return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            bot.logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Result send(SendAble msg) {
        return new Result<>(msg.send(this));
    }

    @Override
    public Result<ActionResult> send(MessagePacket packet) {
        RawPreMessage msg = new RawPreMessage();
        msg.setMsgId(RawMessage.this.id);
        BaseUtils.packet2pre(packet, msg);
        return new Result<ActionResult>(bot.messageBase.send(RawMessage.this.channelId, msg, SEND_MESSAGE_HEADERS));
    }

    @Override
    public Result<ActionResult> send(RawPreMessage msg) {
        return new Result<>(bot.messageBase.send(RawMessage.this.channelId, msg, SEND_MESSAGE_HEADERS));
    }

    @Override
    public Object delete() {
        return bot.messageBase.delete(this.channelId, this.id, false);
    }

    @Override
    public String getCid() {
        return (envType == EnvType.GROUP || envType == EnvType.GROUP_USER) ? getSrcGuildId() : getChannelId();
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

    @Getter
    @JSONField(serialize = false, deserialize = false)
    private Bot bot;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    @Override
    public PinsMessage addPins() {
        PinsMessage pm = new PinsMessage();
        String p0 = getBot().channelBase.addPins(getChannelId(), getMid());
        for (String s : p0.split("&")) {
            String[] kv = s.split("=");
            String key = kv[0];
            String value = kv[1];
            switch (key) {
                case "channel_id":
                    pm.setChannel_id(value);
                    break;
                case "guild_id":
                    pm.setGuild_id(value);
                    break;
                case "message_ids":
                    pm.getMessage_ids().add(value);
                    break;
            }
        }
        return pm;
    }

    @Override
    public void deletePins() {
        getBot().channelBase.deletePins(getChannelId(), getMid());
    }

    @Override
    public PinsMessage getPins() {
        return getBot().channelBase.getPins(getChannelId());
    }

    @Override
    public BaseV2 getV2() {
        return envType == EnvType.GROUP ? bot.groupBaseV2 : envType == EnvType.GROUP_USER ? bot.userBaseV2 : null;
    }
}