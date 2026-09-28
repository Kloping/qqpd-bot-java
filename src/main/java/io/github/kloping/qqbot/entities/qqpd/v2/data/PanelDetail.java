package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 指令面板详情，相比 {@link PanelRecord} 额外返回关联的用户/群 openid 列表。
 * <p>仅 c2c 场景且 target_type=specific 时返回 user_openids，
 * group 场景且 target_type=specific 时返回 group_openids，最多 1000 条。</p>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PanelDetail extends PanelRecord {
    @JSONField(name = "user_openids")
    private List<String> userOpenids;
    @JSONField(name = "group_openids")
    private List<String> groupOpenids;
}
