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
public class MessagePre implements SendAble {
    private String content = "";
    private Image image;
    private String replyId;

    @Override
    public ActionResult send(SenderAndCidMidGetter er) {
        if (image != null) {
            if (image.getBytes() != null) {
                BaseKeyVals keyVals = new BaseKeyVals();
                if (er.getMid() != null) {
                    HttpConnection.KeyVal v0 = HttpConnection.KeyVal.create("msg_id", er.getMid());
                    v0.contentType("text/plain");
                    keyVals.add(v0);
                }
                if (Judge.isNotEmpty(content)) {
                    HttpConnection.KeyVal v1 = HttpConnection.KeyVal.create("content", content);
                    v1.contentType("text/plain");
                    keyVals.add(v1);
                }
                HttpConnection.KeyVal v1 = HttpConnection.KeyVal.create("file_image", image.getName(), new ByteArrayInputStream(image.getBytes()));
                v1.contentType(image.getType());
                keyVals.add(v1);
                return er.getBot().messageBase.send(er.getCid(), SEND_FORM_DATA_HEADERS, keyVals);
            }
        }
        return getActionResult(er, image, content, replyId);
    }

    public static ActionResult getActionResult(SenderAndCidMidGetter er, Image image, String content, String replyId) {
        MessagePacket packet = new MessagePacket();
        if (Judge.isNotEmpty(replyId)) packet.setReplyId(replyId);
        if (Judge.isNotEmpty(content)) packet.setContent(content);
        if (image != null && Judge.isNotEmpty(image.getUrl())) packet.setImage(image.getUrl());
        return er.send(packet);
    }
}