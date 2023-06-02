package io.github.kloping.qqbot.entities.qqpd;

import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.DirectSender;
import io.github.kloping.qqbot.entities.qqpd.message.Message;
import io.github.kloping.qqbot.entities.qqpd.message.PreMessage;
import io.github.kloping.qqbot.entities.qqpd.message.audited.MessageAudited;
import io.github.kloping.qqbot.impl.MessagePacket;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.Data;

import static io.github.kloping.qqbot.entities.qqpd.Channel.MAP;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>guild_id</td> <td>string</td> <td>私信会话关联的频道 id</td></tr> <tr><td>channel_id</td> <td>string</td> <td>私信会话关联的子频道 id</td></tr> <tr><td>create_time</td> <td>string</td> <td>创建私信会话时间戳</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
public class Dms implements DirectSender {
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
    public MessageAudited sendDirect(String text) {
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
    public MessageAudited sendDirect(String text, Message message) {
        return sendDirect(new MessagePacket().setContent(text).setReplyId(message.getId()));
    }

    /**
     * 此方式为主动消息
     *
     * @param packet
     * @return
     */
    @Override
    public MessageAudited sendDirect(MessagePacket packet) {
        PreMessage msg = new PreMessage();
        BaseUtils.packet2pre(packet, msg);
        return Resource.dmsBase.send(this.guildId, msg, MAP);
    }

    @Override
    public MessageAudited sendDirect(PreMessage msg) {
        return Resource.dmsBase.send(this.guildId, msg, MAP);
    }
}
