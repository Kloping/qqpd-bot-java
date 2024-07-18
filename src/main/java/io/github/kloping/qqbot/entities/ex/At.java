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

    private String type = "member";
    private String targetId;

    public At(String type, String targetId) {
        this.type = type;
        this.targetId = targetId;
    }

    public At(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        if (CHANNEL_TYPE.equals(type)) {
            return "<#" + targetId + ">";
        } else if (MEMBER_TYPE.equals(type)) {
            return String.format("<qqbot-at-user id=\"%s\" />", targetId);
        } else return "@";
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
