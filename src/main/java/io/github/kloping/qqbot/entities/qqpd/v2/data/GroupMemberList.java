package io.github.kloping.qqbot.entities.qqpd.v2.data;

import io.github.kloping.qqbot.entities.qqpd.v2.Member;
import lombok.Data;
import lombok.experimental.Accessors;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/** 群成员分页结果。 */
@Data
@Accessors(chain = true)
public class GroupMemberList {
    private List<Member> members;
    @JSONField(name = "next_cursor")
    private String nextCursor;
}
