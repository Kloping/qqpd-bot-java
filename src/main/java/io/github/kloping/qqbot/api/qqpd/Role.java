package io.github.kloping.qqbot.api.qqpd;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>身份组ID</td></tr> <tr><td>name</td> <td>string</td> <td>名称</td></tr> <tr><td>color</td> <td>uint32</td> <td>ARGB的HEX十六进制颜色值转换后的十进制数值</td></tr> <tr><td>hoist</td> <td>uint32</td> <td>是否在成员列表中单独展示: 0-否, 1-是</td></tr> <tr><td>number</td> <td>uint32</td> <td>人数</td></tr> <tr><td>member_limit</td> <td>uint32</td> <td>成员上限</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Role {
    private Number number;
    private Number color;
    private Number memberLimit;
    private String name;
    private String id;
    private Number hoist;
}