package io.github.kloping.qqbot.entities.qqpd.v2.data;

import lombok.Data;
import lombok.experimental.Accessors;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 发送禁言的请求数据
 */
@Data
@Accessors(chain = true)
public class SetMemberMuteState {
    private String op;
    @JSONField(name = "member_openid")
    private String memberOpenid;
    @JSONField(name = "mute_expire_at")
    private String muteExpireAt;
}
