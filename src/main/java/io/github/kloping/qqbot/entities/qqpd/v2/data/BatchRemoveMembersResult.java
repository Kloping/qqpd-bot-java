package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/** Response for a batch member removal request. */
@Data
@Accessors(chain = true)
public class BatchRemoveMembersResult {
    @JSONField(name = "remove_members_result")
    private String removeMembersResult;
    @JSONField(name = "add_to_member_blacklist_fail_openids")
    private List<String> addToMemberBlacklistFailOpenids;
}
