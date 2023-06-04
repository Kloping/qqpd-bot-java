package io.github.kloping.qqbot.entities.ex;

import com.alibaba.fastjson.JSON;
import io.github.kloping.MySpringTool.entity.KeyVals;
import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.entities.qqpd.message.Message;
import io.github.kloping.qqbot.entities.qqpd.message.MessageReference;
import io.github.kloping.qqbot.entities.qqpd.message.audited.MessageAudited;
import io.github.kloping.qqbot.impl.MessagePacket;
import lombok.Data;
import org.jsoup.helper.HttpConnection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static io.github.kloping.qqbot.entities.qqpd.message.Message.SEND_FORM_DATA_HEADERS;

/**
 * @author github.kloping
 */
@Data
public class MessagePre {
    private String content = "";
    private Image image;
    private String replyId;

    public MessageAudited send(Message message) {
        if (image != null) {
            if (image.getBytes() != null) {
                List<HttpConnection.KeyVal> list = new LinkedList<>();
                KeyVals vals = new KeyVals() {
                    @Override
                    public Collection<HttpConnection.KeyVal> values() {
                        return list;
                    }
                };
                HttpConnection.KeyVal v0 = HttpConnection.KeyVal.create("msg_id", message.getId());
                v0.contentType("text/plain");
                list.add(v0);
                if (Judge.isNotEmpty(content)) {
                    HttpConnection.KeyVal v1 = HttpConnection.KeyVal.create("content", content);
                    v1.contentType("text/plain");
                    list.add(v1);
                }
                return Resource.messageBase.send(message.getChannelId(), SEND_FORM_DATA_HEADERS, image.getBytes(), vals);
            }
        }
        MessagePacket packet = new MessagePacket();
        if (Judge.isNotEmpty(replyId)) packet.setReplyId(replyId);
        if (Judge.isNotEmpty(content)) packet.setContent(content);
        if (image != null && Judge.isNotEmpty(image.getUrl())) packet.setImage(image.getUrl());
        return message.send(packet);
    }
}