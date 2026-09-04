package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.entities.qqpd.v2.Member;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/** Paginated group member blacklist. */
@Data
@Accessors(chain = true)
public class MemberBlacklist {
    private List<Member> users;
    @JSONField(name = "next_cursor")
    private String nextCursor;
}
