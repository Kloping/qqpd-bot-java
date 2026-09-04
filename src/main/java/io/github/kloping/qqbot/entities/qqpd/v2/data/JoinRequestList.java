package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 入群申请列表分页结果
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>list</td> <td>[]JoinRequest</td> <td>入群申请列表</td></tr>
 * <tr><td>next_cursor</td> <td>string</td> <td>下一页游标，空串表示已到末页</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class JoinRequestList {
    @JSONField(name = "list")
    private List<JoinRequest> list;
    @JSONField(name = "next_cursor")
    private String nextCursor;
}
