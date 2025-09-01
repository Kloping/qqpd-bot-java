package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.qqpd.Dms;
import io.github.kloping.qqbot.entities.qqpd.message.DirectMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;
import lombok.Data;
import org.jsoup.helper.HttpConnection;

import java.io.ByteArrayInputStream;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_FORM_DATA_HEADERS;

/**
 * 不适配 group
 * @author github.kloping
 */
@Data
public class MessagePre implements SendAble {
    private String content = "";
    private FileMsg fileMsg;
    private String replyId;

    @Override
    public Result<ActionResult> send(SenderAndCidMidGetter er) {
        if (fileMsg != null) {
            if (fileMsg.getBytes() != null) {
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
                HttpConnection.KeyVal v1 = HttpConnection.KeyVal.create("file_image", fileMsg.getName(), new ByteArrayInputStream(fileMsg.getBytes()));
                v1.contentType(fileMsg.getType());
                keyVals.add(v1);
                if (er instanceof Dms) {
                    Dms dms = (Dms) er;
                    return new Result<>(er.getBot().dmsBase.send(dms.getGuildId(), SEND_FORM_DATA_HEADERS, keyVals));
                }else if (er instanceof DirectMessage) {
                    DirectMessage dms = (DirectMessage) er;
                    return new Result<>(er.getBot().dmsBase.send(dms.getGuildId(), SEND_FORM_DATA_HEADERS, keyVals));
                } else return new Result<>(er.getBot().messageBase.send(er.getCid(), SEND_FORM_DATA_HEADERS, keyVals));
            }
        }
        return getActionResult(er, fileMsg, content, replyId);
    }

    public static Result<ActionResult> getActionResult(SenderAndCidMidGetter er, FileMsg fileMsg, String content, String replyId) {
        MessagePacket packet = new MessagePacket();
        if (Judge.isNotEmpty(replyId)) packet.setReplyId(replyId);
        if (Judge.isNotEmpty(content)) packet.setContent(content);
        if (fileMsg != null && Judge.isNotEmpty(fileMsg.getUrl())) packet.setImage(fileMsg.getUrl());
        return er.send(packet);
    }
}