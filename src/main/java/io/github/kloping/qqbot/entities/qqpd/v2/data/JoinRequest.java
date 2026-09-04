package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 入群申请
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>join_request_id</td> <td>string</td> <td>申请ID，需要在申请接口回传</td></tr>
 * <tr><td>risk_tips</td> <td>string</td> <td>安全提示语</td></tr>
 * <tr><td>union_openid</td> <td>string</td> <td>用户在应用/开放平台下的统一标识（如有）</td></tr>
 * <tr><td>member_openid</td> <td>string</td> <td>申请人 openid</td></tr>
 * <tr><td>username</td> <td>string</td> <td>申请人昵称</td></tr>
 * <tr><td>apply_at</td> <td>string</td> <td>申请时间戳（RFC3339 格式）</td></tr>
 * <tr><td>apply_source</td> <td>string</td> <td>申请来源：self_apply 主动申请，invited 被邀请</td></tr>
 * <tr><td>invited_by</td> <td>string</td> <td>邀请人 openid（apply_source=invited 时有效）</td></tr>
 * <tr><td>bot</td> <td>boolean</td> <td>是否为机器人账号</td></tr>
 * <tr><td>verify_info</td> <td>VerifyInfo</td> <td>用户入群验证方式</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class JoinRequest {
    /** 所属群上下文，仅用于便捷审批，不参与 JSON 序列化。 */
    @JSONField(serialize = false, deserialize = false)
    private transient Group group;

    @JSONField(name = "join_request_id")
    private String joinRequestId;
    @JSONField(name = "risk_tips")
    private String riskTips;
    @JSONField(name = "union_openid")
    private String unionOpenid;
    @JSONField(name = "member_openid")
    private String memberOpenid;
    private String username;
    @JSONField(name = "apply_at")
    private String applyAt;
    @JSONField(name = "apply_source")
    private String applySource;
    @JSONField(name = "invited_by")
    private String invitedBy;
    @JSONField(name = "bot")
    private Boolean bot;
    @JSONField(name = "verify_info")
    private VerifyInfo verifyInfo;

    /** 返回申请中的验证答案。问答验证取所有答案，普通验证取验证消息。 */
    public List<String> getAnswers() {
        if (verifyInfo == null) {
            return java.util.Collections.emptyList();
        }
        if (verifyInfo.reviewQaList != null && !verifyInfo.reviewQaList.isEmpty()) {
            return verifyInfo.reviewQaList.stream().map(ReviewQA::getAnswer).collect(Collectors.toList());
        }
        if (verifyInfo.verifyMessage != null) {
            return java.util.Collections.singletonList(verifyInfo.verifyMessage);
        }
        return java.util.Collections.emptyList();
    }

    /** 返回第一个验证答案，便于单问题验证场景使用。 */
    public String getAnswer() {
        return getAnswers().stream().findFirst().orElse("");
    }

    /** 条件满足时通过申请，谓词参数为完整的入群申请数据。 */
    public boolean ifApprove(Predicate<JoinRequest> condition) {
        if (condition == null || !condition.test(this)) {
            return false;
        }
        submit(new JoinApproval().setOp("approve"));
        return true;
    }

    /** 条件满足时拒绝申请，谓词参数为完整的入群申请数据。 */
    public boolean ifDecline(Predicate<JoinRequest> condition) {
        if (condition == null || !condition.test(this)) {
            return false;
        }
        submit(new JoinApproval().setOp("decline"));
        return true;
    }

    private void submit(JoinApproval approval) {
        if (group == null) {
            throw new IllegalStateException("JoinRequest 未关联群上下文，无法执行审批");
        }
        approval.setJoinRequestId(joinRequestId);
        group.approvalJoinRequest(memberOpenid, approval);
    }

    /**
     * 用户入群验证方式
     *
     * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
     * <tbody><tr><td>method</td> <td>string</td> <td>入群验证方式：verify_message / admin_review_qa</td></tr>
     * <tr><td>verify_message</td> <td>string</td> <td>验证消息内容</td></tr>
     * <tr><td>review_qa_list</td> <td>[]ReviewQA</td> <td>问答列表</td></tr></tbody></table>
     */
    @Data
    @Accessors(chain = true)
    public static class VerifyInfo {
        private String method;
        @JSONField(name = "verify_message")
        private String verifyMessage;
        @JSONField(name = "review_qa_list")
        private List<ReviewQA> reviewQaList;
    }

    /**
     * 入群验证问答
     */
    @Data
    @Accessors(chain = true)
    public static class ReviewQA {
        @JSONField(name = "question")
        private String question;
        @JSONField(name = "answer")
        private String answer;
    }
}
