package io.github.kloping.qqbot.entities.qqpd.v2;

import lombok.Getter;

/** 成员禁言操作类型。 */
@Getter
public enum Mute {
    /**
     * 新增禁言类型
     */
    Add("add"),
    /**
     * 更新禁言类型
     */
    Update("update");

    private final String value;

    Mute(String value) { this.value = value; }

}
