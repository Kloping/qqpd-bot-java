package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 指令面板列表分页结果，按设置时间倒序排列。
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>records</td> <td>[]PanelRecord</td> <td>面板记录列表</td></tr>
 * <tr><td>next_cursor</td> <td>string</td> <td>下一页游标，空串表示已到最后一页</td></tr>
 * <tr><td>is_end</td> <td>boolean</td> <td>是否已拉取到最后一页</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class PanelRecordPage {
    private List<PanelRecord> records;
    @JSONField(name = "next_cursor")
    private String nextCursor;
    @JSONField(name = "is_end")
    private Boolean isEnd;
}
