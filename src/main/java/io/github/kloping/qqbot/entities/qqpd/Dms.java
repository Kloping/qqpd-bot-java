package io.github.kloping.qqbot.entities.qqpd;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.DirectSender;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.Data;
import lombok.Getter;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>guild_id</td> <td>string</td> <td>私信会话关联的频道 id</td></tr> <tr><td>channel_id</td> <td>string</td> <td>私信会话关联的子频道 id</td></tr> <tr><td>create_time</td> <td>string</td> <td>创建私信会话时间戳</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
public class Dms implements DirectSender, SenderAndCidMidGetter {
    private String guildId;
    private String channelId;
    private String createTime;

    /**
     * 此方式为主动消息
     *
     * @param text
     * @return
     */
    @Override
    public Result<ActionResult> sendDirect(String text) {
        return sendDirect(new MessagePacket().setContent(text));
    }

    /**
     * 此方式为主动消息
     *
     * @param text
     * @param message
     * @return
     */
    @Override
    public Result<ActionResult> sendDirect(String text, RawMessage message) {
        return sendDirect(new MessagePacket().setContent(text).setReplyId(message.getId()));
    }

    /**
     * 此方式为主动消息
     *
     * @param packet
     * @return
     */
    @Override
    public Result<ActionResult> sendDirect(MessagePacket packet) {
        RawPreMessage msg = new RawPreMessage();
        BaseUtils.packet2pre(packet, msg);
        return new Result<>(bot.dmsBase.send(this.guildId, msg, SEND_MESSAGE_HEADERS));
    }

    @Override
    public Result<ActionResult> sendDirect(RawPreMessage msg) {
        return new Result<>(bot.dmsBase.send(this.guildId, msg, SEND_MESSAGE_HEADERS));
    }

    @Override
    public Result<ActionResult> send(SendAble msg) {
        return msg.send(this);
    }

    @Override
    public String getCid() {
        return channelId;
    }

    @Getter
    @JSONField(serialize = false, deserialize = false)
    private Bot bot;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    @Override
    public EnvType getEnvType() {
        return EnvType.GUILD;
    }
}
