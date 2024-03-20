package io.github.kloping.qqbot.entities.qqpd;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.BaseV2;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import io.github.kloping.qqbot.impl.MessagePacket;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>平台方事件 ID，可以用于被动消息发送</td></tr> <tr><td>type</td> <td>int</td> <td>消息按钮： 11，单聊快捷菜单：12</td></tr> <tr><td>scene</td> <td>string</td> <td>事件发生的场景：c2c、group、guild</td></tr> <tr><td>chat_type</td> <td>int</td> <td>0 频道场景，1 群聊场景，2 单聊场景</td></tr> <tr><td>timestamp</td> <td>string</td> <td>触发时间 RFC 3339 格式</td></tr> <tr><td>guild_id</td> <td>string</td> <td>频道的 openid ，仅在频道场景提供该字段</td></tr> <tr><td>channel_id</td> <td>string</td> <td>文字子频道的 openid，仅在频道场景提供该字段</td></tr> <tr><td>user_openid</td> <td>string</td> <td>单聊单聊按钮触发x，的用户 openid，仅在单聊场景提供该字段</td></tr> <tr><td>group_openid</td> <td>string</td> <td>群的 openid，仅在群聊场景提供该字段</td></tr> <tr><td>group_member_openid</td> <td>string</td> <td>按钮触发用户，群聊的群成员 openid，仅在群聊场景提供该字段</td></tr> <tr><td>data.resoloved.button_data</td> <td>string</td> <td>操作按钮的 data 字段值（在发送消息按钮时设置）</td></tr> <tr><td>data.resoloved.button_id</td> <td>string</td> <td>操作按钮的 id 字段值（在发送消息按钮时设置）</td></tr> <tr><td>data.resoloved.user_id</td> <td>string</td> <td>操作的用户 userid，仅频道场景提供该字段</td></tr> <tr><td>data.resoloved.feature_id</td> <td>string</td> <td>操作按钮的 id 字段值，仅自定义菜单提供该字段（在管理端设置）</td></tr> <tr><td>data.resoloved.message_id</td> <td>string</td> <td>操作的消息id，目前仅频道场景提供该字段</td></tr> <tr><td>version</td> <td>int</td> <td>默认 1</td></tr> <tr><td>application_id</td> <td>string</td> <td>机器人的 appid</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
public class InterAction implements SenderV2, SenderAndCidMidGetter {
    private String id;
    private Integer type;
    private String scene;
    private Integer chat_type;
    private String timestamp;

    private String guild_id;
    private String channel_id;

    private String user_openid;
    private String group_openid;
    private String group_member_openid;

    private DataR data;
    private String version;

    private String application_id;

    @Data
    public static class DataR {
        private Resolved resolved;
        private Integer type;
    }

    @Data
    public static class Resolved {
        private String button_data;
        private String button_id;
        private String user_id;
        private String feature_id;
        private String message_id;
    }

    @Override
    public String getCid() {
        return getEnvType() == EnvType.GUILD ? channel_id : group_openid;
    }

    @Override
    public String getMid() {
        return id;
    }

    @Override
    public EnvType getEnvType() {
        return chat_type == 0 ? EnvType.GUILD : EnvType.GROUP;
    }

    @Setter
    @Getter
    private Bot bot;

    @Override
    public BaseV2 getV2() {
        return bot.groupBaseV2;
    }

    @Override
    public Result send(String text) {
        if (getEnvType() == EnvType.GUILD) return send(new MessagePacket().setContent(text).setReplyId(getMid()));
        else if (getEnvType() == EnvType.GROUP || getEnvType() == EnvType.GROUP_USER) {
            V2MsgData data = new V2MsgData().setMsg_id(getMid()).setContent(text).setMsg_seq(getMsgSeq());
            return new Result<V2Result>(getV2().send(getCid(), data.toString(), SEND_MESSAGE_HEADERS));
        } else return null;
    }

    @Override
    public Result send(String text, RawMessage message) {
        if (getEnvType() == EnvType.GUILD)
            return send(new MessagePacket().setContent(text).setReplyId(message.getId()));
        else if (getEnvType() == EnvType.GROUP || getEnvType() == EnvType.GROUP_USER) {
            V2MsgData data = new V2MsgData().setMsg_id(message.getId()).setContent(text).setMsg_seq(getMsgSeq());
            return new Result<V2Result>(getV2().send(message.getSrcGuildId(), data.toString(), SEND_MESSAGE_HEADERS));
        } else return null;
    }

    @Override
    public Result send(MessagePacket packet) {
        RawPreMessage msg = new RawPreMessage();
        msg.setMsgId(id);
        BaseUtils.packet2pre(packet, msg);
        return new Result<ActionResult>(bot.messageBase.send(getCid(), msg, SEND_MESSAGE_HEADERS));
    }

    @Override
    public Result send(SendAble msg) {
        return msg.send(this);
    }
}
