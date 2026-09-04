package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/** 群黑名单操作结果。 */
@Data
@Accessors(chain = true)
public class MemberBlacklistResult {
    @JSONField(name = "fail_openids")
    private List<String> failOpenids;
}
