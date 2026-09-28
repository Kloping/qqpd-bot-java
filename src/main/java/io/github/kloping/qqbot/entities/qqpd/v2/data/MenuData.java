package io.github.kloping.qqbot.entities.qqpd.v2.data;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 全局自定义菜单查询结果，同时用作修改菜单的请求体。
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>version</td> <td>integer</td> <td>当前菜单的版本号（请求时无需携带）</td></tr>
 * <tr><td>menu</td> <td>Menu</td> <td>当前生效的菜单配置，未设置过菜单时为空</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class MenuData {
    private Integer version;
    private Menu menu;
}
