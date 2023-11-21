package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.Bot;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 群聊/单聊 父
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public abstract class Contact implements SenderAndCidMidGetter {
    @JSONField(serialize = false, deserialize = false)
    protected JSONObject meta;

    public Contact() {
    }

    public Contact(JSONObject mate) {
        this.meta = mate;
    }

    protected String id;
    protected String openid;

    @Getter
    protected Bot bot;

    @Override
    public void setBot(Bot bot) {
        this.bot = bot;
    }

}
