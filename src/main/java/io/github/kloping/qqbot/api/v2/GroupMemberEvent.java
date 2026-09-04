package io.github.kloping.qqbot.api.v2;

/**
 * 群成员变更事件基类。
 *
 * <p>对应 QQ 开放平台 {@code GROUP_MEMBER_EVENT}（{@code 1 << 24}）Intent，
 * 由 {@link GroupMemberAddEvent} 和 {@link GroupMemberRemoveEvent} 继承。</p>
 *
 * <p>事件数据字段：</p>
 * <ul>
 *     <li>{@code timestamp}：事件时间戳，Unix 秒。</li>
 *     <li>{@code group_openid}：群 OpenID，可通过 {@link GroupEvent#getGroup()} 获取群对象。</li>
 *     <li>{@code member_openid}：发生加入或退出的群成员 OpenID。</li>
 *     <li>{@code user_openid}：成员的用户 OpenID（跨应用统一标识，可能为空）。</li>
 * </ul>
 */
public interface GroupMemberEvent extends GroupEvent {
    /**
     * 获取事件时间戳。
     *
     * @return Unix 秒时间戳
     */
    Long getTimestamp();

    /**
     * 获取发生变更的群成员 OpenID。
     *
     * @return 群成员 OpenID
     */
    String getMemberOpenid();

    /**
     * 获取成员的用户 OpenID。
     *
     * @return 用户 OpenID，未提供时可能为 {@code null}
     */
    String getUserOpenid();
}
