package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import lombok.EqualsAndHashCode;

/**
 * @author github.kloping
 */
@EqualsAndHashCode(callSuper = true)
public class Member extends Contact implements SenderAndCidMidGetter {
    public Member(JSONObject mate) {
        super(mate);
        this.setId(this.getMeta().getString("id"));
        this.setOpenid(this.getMeta().getString("member_openid"));
    }

    @Override
    public Result<V2Result> send(String text) {
        V2MsgData data = new V2MsgData().setContent(text);
        return new Result<V2Result>(bot.userBaseV2.send(getOpenid(), JSON.toJSONString(data), Channel.SEND_MESSAGE_HEADERS));
    }

    private V2Result sendImage(Image msg) {
        if (RawMessage.ImagePrepare(msg, bot)) return null;
        return bot.userBaseV2.sendFile(getOpenid(), String.format("{\"file_type\": %s,\"url\": \"%s\",\"srv_send_msg\": true}", msg.getFile_type(), msg.getUrl()), Channel.SEND_MESSAGE_HEADERS);
    }

    @Override
    public Result<V2Result> send(String text, RawMessage message) {
        return message.send(text);
    }

    @Override
    public Result send(SendAble msg) {
        if (msg instanceof Image) {
            return new Result(sendImage((Image) msg));
        } else return msg.send(this);
    }

    @Override
    public String getCid() {
        return getOpenid();
    }

    @Override
    public EnvType getEnvType() {
        return EnvType.GROUP_USER;
    }
}
