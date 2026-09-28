package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 指令面板列表记录。
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>panel_id</td> <td>string</td> <td>面板 ID</td></tr>
 * <tr><td>scope</td> <td>string</td> <td>生效场景：c2c/group/channel/dm</td></tr>
 * <tr><td>target_type</td> <td>string</td> <td>作用范围：all/specific</td></tr>
 * <tr><td>panel</td> <td>Panel</td> <td>面板配置内容</td></tr>
 * <tr><td>created_at</td> <td>string</td> <td>创建时间，RFC3339 格式</td></tr>
 * <tr><td>updated_at</td> <td>string</td> <td>更新时间，RFC3339 格式</td></tr>
 * <tr><td>version</td> <td>integer</td> <td>面板版本号</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class PanelRecord {
    @JSONField(name = "panel_id")
    private String panelId;
    private String scope;
    @JSONField(name = "target_type")
    private String targetType;
    private Panel panel;
    @JSONField(name = "created_at")
    private String createdAt;
    @JSONField(name = "updated_at")
    private String updatedAt;
    private Integer version;
}
