package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.http.BaseV2;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import lombok.EqualsAndHashCode;

/**
 * @author github.kloping
 */
@EqualsAndHashCode(callSuper = true)
public class Friend extends Contact implements SenderAndCidMidGetter, SenderV2 {
    public Friend(JSONObject mate) {
        super(mate);
        if (getMeta().containsKey("openid")) {
            String id = getMeta().getString("openid");
            this.setId(id);
            this.setOpenid(openid);
        } else {
            this.setId(this.getMeta().getString("id"));
            this.setOpenid(this.getMeta().getString("user_openid"));
        }
    }

    @Override
    public Result<V2Result> send(String text) {
        V2MsgData data = new V2MsgData().setContent(text);
        return new Result<V2Result>(bot.userBaseV2.send(getOpenid(), JSON.toJSONString(data), Channel.SEND_MESSAGE_HEADERS));
    }

    @Override
    public Result<V2Result> send(String text, RawMessage message) {
        return message.send(text);
    }

    @Override
    public Result send(SendAble msg) {
        return msg.send(this);
    }

    @Override
    public String getCid() {
        return getOpenid();
    }

    @Override
    public EnvType getEnvType() {
        return EnvType.GROUP_USER;
    }

    @Override
    public BaseV2 getV2() {
        return getBot().userBaseV2;
    }
}
