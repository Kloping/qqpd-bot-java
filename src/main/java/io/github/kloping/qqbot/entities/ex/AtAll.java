package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;

/**
 * @author github.kloping
 */
public class AtAll implements SendAble {

    private boolean old;

    public AtAll() {
        this(false);
    }

    public AtAll(boolean old) {
        this.old = old;
    }

    @Override
    public Result send(SenderAndCidMidGetter er) {
        if (er.getEnvType() == EnvType.GUILD) {
            MessagePacket packet = new MessagePacket();
            if (old) packet.setContent("@everyone");
            else packet.setContent("<qqbot-at-everyone/>");
            return er.send(packet);
        } else {
            return er.send("@所有人");
        }
    }

    @Override
    public String toString() {
        return "@atAll";
    }
}
