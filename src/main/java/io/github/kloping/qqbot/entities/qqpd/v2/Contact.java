package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 群聊/单聊 父
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class Contact {
    @JSONField(serialize = false, deserialize = false)
    protected JSONObject meta;

    public Contact() {
    }

    public Contact(JSONObject mate) {
        this.meta = mate;
    }

    protected String id;
    protected String openid;
}
