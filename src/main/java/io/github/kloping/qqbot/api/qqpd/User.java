package io.github.kloping.qqbot.api.qqpd;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <div class="theme-default-content content__default"><h1 id="用户对象-user"><a href="#用户对象-user" class="header-anchor">#</a>
 *     用户对象(User)</h1>
 *     <p>用户对象中所涉及的 ID 类数据，都仅在机器人场景流通，与真实的 ID 无关。请不要理解为真实的 ID</p>
 *     <h3 id="user"><a href="#user" class="header-anchor">#</a> User</h3>
 *     <br>
 *     <table>
 *         <thead>
 *         <tr>
 *             <th>-字段名-</th>
 *             <th>--类型--</th>
 *             <th>--描述--</th>
 *         </tr>
 *         </thead>
 *         <tbody>
 *         <tr>
 *             <td>id</td>
 *             <td>string</td>
 *             <td>用户 id</td>
 *         </tr>
 *         <tr>
 *             <td>username</td>
 *             <td>string</td>
 *             <td>用户名</td>
 *         </tr>
 *         <tr>
 *             <td>avatar</td>
 *             <td>string</td>
 *             <td>用户头像地址</td>
 *         </tr>
 *         <tr>
 *             <td>bot</td>
 *             <td>bool</td>
 *             <td>是否是机器人</td>
 *         </tr>
 *         <tr>
 *             <td>union_openid</td>
 *             <td>string</td>
 *             <td>特殊关联应用的 openid，需要特殊申请并配置后才会返回。如需申请，请联系平台运营人员。</td>
 *         </tr>
 *         <tr>
 *             <td>union_user_account</td>
 *             <td>string</td>
 *             <td>机器人关联的互联应用的用户信息，与union_openid关联的应用是同一个。如需申请，请联系平台运营人员。</td>
 *         </tr>
 *         </tbody>
 *     </table>
 *     <h3 id="提示"><a href="#提示" class="header-anchor">#</a> 提示</h3>
 *     <p><code>union_openid</code> 与 <code>union_user_account</code> 只有在单独拉取 member 信息的时候才会提供，在其他的事件中所携带的 user
 *         对象，均无这两个字段的内容。</p></div>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class User {
	private String id;
	private String username;
	private String avatar;
	private boolean bot;
	private String union_openid;
	private String union_user_account;
}
