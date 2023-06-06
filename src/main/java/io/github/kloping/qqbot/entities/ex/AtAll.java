package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.impl.MessagePacket;

/**
 * @author github.kloping
 */
public class AtAll implements SendAble {

    @Override
    public ActionResult send(SenderAndCidMidGetter er) {
        MessagePacket packet = new MessagePacket();
        packet.setContent("@everyone");
        return er.send(packet);
    }
}
