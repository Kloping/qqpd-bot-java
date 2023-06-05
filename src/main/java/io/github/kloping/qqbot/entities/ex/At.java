package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.qqpd.message.audited.MessageAudited;
import io.github.kloping.qqbot.impl.MessagePacket;
import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class At implements SendAble {
    private String type;
    private String targetId;

    public At(String type, String targetId) {
        this.type = type;
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        if (type == "channel") {
            return "<#" + targetId + ">";
        } else if (type == "member") {
            return "<@" + targetId + ">";
        } else return "@";
    }

    @Override
    public MessageAudited send(SenderAndCidMidGetter er) {
        MessagePacket packet = new MessagePacket();
        packet.setContent(toString());
        return er.send(packet);
    }
}
