package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/** 群黑名单操作请求。 */
@Data
@Accessors(chain = true)
public class MemberBlacklistRequest {
    /** 操作类型：add 加入，del 移出。 */
    private String op;
    @JSONField(name = "member_openids")
    private List<String> memberOpenids;
}
