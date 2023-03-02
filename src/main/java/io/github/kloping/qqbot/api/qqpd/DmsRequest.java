package io.github.kloping.qqbot.api.qqpd;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>recipient_id</td> <td>string</td> <td>接收者 id</td></tr> <tr><td>source_guild_id</td> <td>string</td> <td>源频道 id</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
public class DmsRequest {
    @JSONField(name = "recipient_id")
    private String recipientId;
    @JSONField(name = "source_guild_id")
    private String sourceGuildId;
}
