package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinRequest;

/**
 * 用户申请加入群事件。
 *
 * <p>事件名：{@code GROUP_JOIN_REQUEST}；订阅所需 Intent：
 * {@code GROUP_MEMBER_EVENT}（{@code 1 << 24}）。仅当机器人为群管理员时，
 * 才可以收到该事件。</p>
 *
 * <p>事件包含申请人的 OpenID、昵称、申请时间、申请来源、风险提示、入群验证信息，
 * 以及自动审批信息等。完整原始字段可通过 {@link Event#getMetadata()} 获取。</p>
 */
public interface GroupJoinRequestEvent extends GroupEvent {
    /**
     * 获取入群申请数据。
     *
     * @return 入群申请对象
     */
    JoinRequest getJoinRequest();

    /**
     * 获取申请 ID，可用于调用入群申请审批接口。
     *
     * @return 申请 ID
     */
    default String getJoinRequestId() {
        return getJoinRequest().getJoinRequestId();
    }

    /**
     * 获取申请人的群成员 OpenID。
     *
     * @return 申请人群成员 OpenID
     */
    default String getMemberOpenid() {
        return getJoinRequest().getMemberOpenid();
    }

    /**
     * 获取申请人昵称。
     *
     * @return 申请人昵称
     */
    default String getUsername() {
        return getJoinRequest().getUsername();
    }

    /**
     * 获取申请时间，RFC 3339 格式。
     *
     * @return 申请时间字符串
     */
    default String getApplyAt() {
        return getJoinRequest().getApplyAt();
    }

    /**
     * 获取安全提示语。
     *
     * @return 风险提示语，普通情况下可能为空
     */
    default String getRiskTips() {
        return getJoinRequest().getRiskTips();
    }

    /**
     * 获取用户在应用或开放平台下的统一 OpenID。
     *
     * @return 统一 OpenID，未提供时可能为 {@code null}
     */
    default String getUnionOpenid() {
        return getJoinRequest().getUnionOpenid();
    }

    /**
     * 获取申请来源。
     *
     * @return {@code self_apply}（主动申请）或 {@code invited}（被邀请）
     */
    default String getApplySource() {
        return getJoinRequest().getApplySource();
    }

    /**
     * 获取邀请人 OpenID。
     *
     * @return 邀请人 OpenID；非被邀请场景可能为空
     */
    default String getInvitedBy() {
        return getJoinRequest().getInvitedBy();
    }

    /**
     * 判断申请人是否为机器人账号。
     *
     * @return 是否为机器人，未提供时可能为 {@code null}
     */
    default Boolean isBot() {
        return getJoinRequest().getBot();
    }

    /**
     * 获取入群验证信息。
     *
     * @return 验证方式及验证内容
     */
    default JoinRequest.VerifyInfo getVerifyInfo() {
        return getJoinRequest().getVerifyInfo();
    }

    /**
     * 获取自动审批扩展信息。
     *
     * @return 自动审批信息；非自动审批场景为空
     */
    default JoinRequest.AutoApproved getAutoApproved() {
        return getJoinRequest().getAutoApproved();
    }
}
