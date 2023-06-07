package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.impl.MessagePacket;
import lombok.Data;
import org.jsoup.helper.HttpConnection;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_FORM_DATA_HEADERS;

/**
 * @author github.kloping
 */
@Data
public class Image implements SendAble {
    private String url;
    private byte[] bytes;

    public Image(byte[] bytes) {
        this.bytes = bytes;
    }

    public Image(String url) {
        this.url = url;
    }

    @Override
    public ActionResult send(SenderAndCidMidGetter er) {
        if (getBytes() != null) {
            BaseKeyVals keyVals = new BaseKeyVals();
            if (er.getMid() != null) {
                HttpConnection.KeyVal v0 = HttpConnection.KeyVal.create("msg_id", er.getMid());
                v0.contentType("text/plain");
                keyVals.add(v0);
            }
            return er.getBot().messageBase.send(er.getCid(), SEND_FORM_DATA_HEADERS, getBytes(), keyVals);
        }
        MessagePacket packet = new MessagePacket();
        if (Judge.isNotEmpty(getUrl())) packet.setImage(getUrl());
        return er.send(packet);
    }
}
