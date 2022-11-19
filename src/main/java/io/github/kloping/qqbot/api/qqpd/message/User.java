package io.github.kloping.qqbot.api.qqpd.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>用户 id</td></tr> <tr><td>username</td> <td>string</td> <td>用户名</td></tr> <tr><td>avatar</td> <td>string</td> <td>用户头像地址</td></tr> <tr><td>bot</td> <td>bool</td> <td>是否是机器人</td></tr> <tr><td>union_openid</td> <td>string</td> <td>特殊关联应用的 openid，需要特殊申请并配置后才会返回。如需申请，请联系平台运营人员。</td></tr> <tr><td>union_user_account</td> <td>string</td> <td>机器人关联的互联应用的用户信息，与union_openid关联的应用是同一个。如需申请，请联系平台运营人员。</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class User {
    private String id;
    private String username;
    private String avatar;
    private Boolean bot;
    private String unionOpenid;
    private String unionUserAccount;
}