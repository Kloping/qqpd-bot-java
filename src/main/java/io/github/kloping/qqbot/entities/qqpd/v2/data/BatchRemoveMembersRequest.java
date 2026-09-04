package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/** Request for removing up to 20 group members. */
@Data
@Accessors(chain = true)
public class BatchRemoveMembersRequest {
    @JSONField(name = "member_openids")
    private List<String> memberOpenids;
    @JSONField(name = "add_to_member_blacklist")
    private Boolean addToMemberBlacklist;
}
