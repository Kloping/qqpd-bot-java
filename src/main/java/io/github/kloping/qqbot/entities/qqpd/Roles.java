package io.github.kloping.qqbot.entities.qqpd;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <table>
 *     <thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead>
 *     <tbody><tr><td>guild_id</td> <td>string</td> <td>频道 ID</td></tr>
 *     <tr><td>roles</td> <td>{@link Role}</a> 对象数组</td> <td>一组频道身份组对象</td></tr> <tr><td>role_num_limit</td> <td>string</td> <td>默认分组上限</td></tr></tbody>
 * </table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Roles {
    private String roleNumLimit;
    private Role[] roles;
    private String guildId;
}