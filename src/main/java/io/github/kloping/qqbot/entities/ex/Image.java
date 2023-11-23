package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import io.github.kloping.qqbot.impl.MessagePacket;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jsoup.helper.HttpConnection;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_FORM_DATA_HEADERS;
import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 *
 * 发送到q群时必须使用url构造参数
 * <br>
 * 发送到频道时url必须备案(少数情况不需要
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class Image implements SendAble {
    private String url;
    private byte[] bytes;
    private String type = "image/jpeg";
    private String name = UUID.randomUUID() + ".jpg";

    /**
     * 仅用于qq群发送
     * <td> 媒体类型：1 图片，2 视频，3 语音，4 文件（暂不开放）<br>资源格式要求<br>图片：png/jpg，视频：mp4，语音：silk</td>
     */
    private Integer file_type = 1;

    public Image(byte[] bytes) {
        this.bytes = bytes;
    }

    public Image(String url) {
        this.url = url;
    }

    public Image(String url, Integer file_type) {
        this.url = url;
        this.file_type = file_type;
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
    public Result send(SenderAndCidMidGetter er) {
        if (er.getEnvType() == EnvType.GUILD) {
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
                return new Result<>(er.getBot().messageBase.send(er.getCid(), SEND_FORM_DATA_HEADERS, keyVals));
            }
            MessagePacket packet = new MessagePacket();
            if (Judge.isNotEmpty(getUrl())) packet.setImage(getUrl());
            return er.send(packet);
        } else {
            SenderV2 v2 = (SenderV2) er;
            if (RawMessage.imagePrepare(this, er.getBot())) return null;
            V2Result result = v2.getV2().sendFile(er.getCid(), String.format("{\"file_type\": %s,\"url\": \"%s\",\"srv_send_msg\": false}", getFile_type(), getUrl()), Channel.SEND_MESSAGE_HEADERS);
            result.logFileInfo(er.getBot().logger);
            V2MsgData data = new V2MsgData();
            data.setMsg_type(7);
            if (Judge.isNotEmpty(er.getMid())) data.setMsg_id(er.getMid());
            data.setMedia(new V2MsgData.Media(result.getFile_info()));
            data.setMsg_seq(v2.getMsgSeq());
            return new Result<V2Result>(v2.getV2().send(er.getCid(), data.toString(), SEND_MESSAGE_HEADERS));
        }
    }
}
