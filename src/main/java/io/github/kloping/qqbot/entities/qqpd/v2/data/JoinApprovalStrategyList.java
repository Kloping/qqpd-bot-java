package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 入群自动审批策略列表分页结果。
 */
@Data
@Accessors(chain = true)
public class JoinApprovalStrategyList {
    private List<JoinApprovalStrategy> strategies;
    @JSONField(name = "next_cursor")
    private String nextCursor;
}
