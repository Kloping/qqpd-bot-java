package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 入群申请审批参数
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>必填</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>op</td> <td>string</td> <td>是</td> <td>审批动作：approve 通过，decline 拒绝</td></tr>
 * <tr><td>join_request_id</td> <td>string</td> <td>否</td> <td>申请ID</td></tr>
 * <tr><td>reject_reason</td> <td>string</td> <td>否</td> <td>拒绝理由，op=decline 时可填</td></tr>
 * <tr><td>add_to_member_blacklist</td> <td>boolean</td> <td>否</td> <td>是否同时加入群黑名单，默认 false，op=decline 时可填</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class JoinApproval {
    @JSONField(name = "op")
    private String op;
    @JSONField(name = "join_request_id")
    private String joinRequestId;
    @JSONField(name = "reject_reason")
    private String rejectReason;
    @JSONField(name = "add_to_member_blacklist")
    private Boolean addToMemberBlacklist;
}
