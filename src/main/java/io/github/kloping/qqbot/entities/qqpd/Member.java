package io.github.kloping.qqbot.entities.qqpd;

import io.github.kloping.qqbot.api.OpAble;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>user</td> <td><a href="/wiki/develop/api/openapi/user/model.html#user" class="">User</a></td> <td>用户的频道基础信息，只有成员相关接口中会填充此信息</td></tr> <tr><td>nick</td> <td>string</td> <td>用户的昵称</td></tr> <tr><td>roles</td> <td>string 数组</td> <td>用户在频道内的身份组ID, 默认值可参考<a href="/wiki/develop/api/openapi/guild/role_model.html#DefaultRoles" class="">DefaultRoles</a></td></tr> <tr><td>joined_at</td> <td>ISO8601 timestamp</td> <td>用户加入频道的时间</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Member implements OpAble {
    private String nick;
    private String joinedAt;
    private String[] roles;
    private User user;
    private String opUserId;
}