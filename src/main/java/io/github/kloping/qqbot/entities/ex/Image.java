package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.impl.MessagePacket;
import lombok.Data;
import org.jsoup.helper.HttpConnection;

import java.io.ByteArrayInputStream;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_FORM_DATA_HEADERS;

/**
 * @author github.kloping
 */
@Data
public class Image implements SendAble {
    private String url;
    private byte[] bytes;
    private String type = "image/jpeg";
    private String name = "temp.jpg";

    public Image(byte[] bytes) {
        this.bytes = bytes;
    }

    public Image(String url) {
        this.url = url;
    }

    public Image(byte[] bytes, String type) {
        this.bytes = bytes;
        this.type = type;
    }

    public Image(byte[] bytes, String type, String name) {
        this.bytes = bytes;
        this.type = type;
        this.name = name;
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
            HttpConnection.KeyVal v1 = HttpConnection.KeyVal.create("file_image", name);
            v1.inputStream(new ByteArrayInputStream(bytes));
            v1.contentType(type);
            keyVals.add(v1);
            return er.getBot().messageBase.send(er.getCid(), SEND_FORM_DATA_HEADERS, keyVals);
        }
        MessagePacket packet = new MessagePacket();
        if (Judge.isNotEmpty(getUrl())) packet.setImage(getUrl());
        return er.send(packet);
    }
}
