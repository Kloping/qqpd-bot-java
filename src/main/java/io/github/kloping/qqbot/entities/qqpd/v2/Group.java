package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSONObject;
import lombok.experimental.Accessors;

/**
 * @author github.kloping
 */
@Accessors(chain = true)
public class Group extends Contact {

    public Group(JSONObject mate) {
        super(mate);
        this.setId(getMeta().getString("group_id"));
        this.setOpenid(getMeta().getString("group_openid"));
    }
}
