package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;
import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class At implements SendAble {
    public static final String MEMBER_TYPE = "member";
    public static final String CHANNEL_TYPE = "channel";

    private String type;
    private String targetId;

    /**
     * 参考 <a href="https://bot.q.qq.com/wiki/develop/api-v2/server-inter/message/trans/text-chain.html#%E4%BD%BF%E7%94%A8-%E8%83%BD%E5%8A%9B">官方文档</a>
     * @param type     成语或子频道
     * @param targetId 目标
     * @param old      旧格式
     */
    public At(String type, String targetId, boolean old) {
        this.type = type;
        this.targetId = targetId;
        this.old = old;
    }

    public At(String type, String targetId) {
        this(type, targetId, false);
    }

    public At(String targetId) {
        this(MEMBER_TYPE, targetId);
    }


    private boolean old = false;

    @Override
    public String toString() {
        if (CHANNEL_TYPE.equals(type)) {
            return "<#" + targetId + ">";
        } else if (MEMBER_TYPE.equals(type)) {
            return old ? String.format("<@%s>", targetId) : String.format("<qqbot-at-user id=\"%s\" />", targetId);
        } else return "<@userid>";
    }

    @Override
    public Result send(SenderAndCidMidGetter er) {
        if (er.getEnvType() == EnvType.GUILD) {
            MessagePacket packet = new MessagePacket();
            packet.setContent(toString());
            return er.send(packet);
        } else {
            return er.send(toString());
        }
    }
}
