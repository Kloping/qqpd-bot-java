package io.github.kloping.qqbot.entities.qqpd.v2.data;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 仅包含版本号的响应，用于修改菜单、修改指令面板等接口。
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class VersionData {
    /** 本次修改后的版本号。 */
    private Integer version;
}
