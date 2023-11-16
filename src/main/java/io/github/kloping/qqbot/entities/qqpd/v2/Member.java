package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSONObject;
import lombok.EqualsAndHashCode;

/**
 * @author github.kloping
 */
@EqualsAndHashCode(callSuper = true)
public class Member extends Contact {
    public Member(JSONObject mate) {
        super(mate);
        this.setId(this.getMeta().getString("id"));
        this.setOpenid(this.getMeta().getString("member_openid"));
    }
}
