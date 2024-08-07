package io.github.kloping.qqbot.entities.ex.enums;

/**
 * 发送环境
 *
 * @author github.kloping
 */
public enum EnvType {
    GUILD, GROUP, GROUP_USER, USER;

    public boolean isV2() {
        return this == GROUP_USER || this == USER || this == GROUP;
    }
}
