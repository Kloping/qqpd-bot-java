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
public class PlainText implements SendAble {
    private String text;

    public PlainText(String text) {
        this.text = text;
    }

    @Override
    public Result send(SenderAndCidMidGetter er) {
        if (er.getEnvType() == EnvType.GUILD) {
            MessagePacket packet = new MessagePacket();
            packet.setContent(toString());
            return er.send(packet);
        } else if (er.getEnvType() == EnvType.GROUP) {
            return er.send(text);
        } else return null;
    }

    @Override
    public String toString() {
        return text;
    }
}
