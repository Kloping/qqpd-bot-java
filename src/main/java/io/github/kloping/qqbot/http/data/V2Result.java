package io.github.kloping.qqbot.http.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>消息唯一ID</td></tr> <tr><td>timestamp</td> <td>int</td> <td>发送时间</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
public class V2Result {
    private String id;
    private Long timestamp;
}
