package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 入群自动审批策略。
 */
@Data
@Accessors(chain = true)
public class JoinApprovalStrategy {
    @JSONField(name = "strategy_id")
    private String strategyId;
    @JSONField(name = "group_openids")
    private List<String> groupOpenids;
    @JSONField(name = "group_ids")
    private List<String> groupIds;
    @JSONField(name = "whitelist_user_count")
    private Integer whitelistUserCount;
    @JSONField(name = "is_enable")
    private String isEnable;
    @JSONField(name = "expire_at")
    private String expireAt;
    @JSONField(name = "created_at")
    private String createdAt;
    @JSONField(name = "updated_at")
    private String updatedAt;
    private String remark;
}
