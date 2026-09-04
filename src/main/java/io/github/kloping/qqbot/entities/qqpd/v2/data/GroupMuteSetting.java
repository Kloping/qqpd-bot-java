package io.github.kloping.qqbot.entities.qqpd.v2.data;

import io.github.kloping.qqbot.entities.qqpd.v2.Member;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/** 群禁言状态查询结果。 */
@Data
@Accessors(chain = true)
public class GroupMuteSetting {
    @JSONField(name = "global_rule") private GlobalMuteRule globalRule;
    private List<MemberMuteState> members;

    @Data
    @Accessors(chain = true)
    public static class GlobalMuteRule {
        private String mode;
        @JSONField(name = "schedule_rules") private List<MuteScheduleRule> scheduleRules;
        @JSONField(name = "recurring_rules") private List<MuteRecurringRule> recurringRules;

        @Data
        @Accessors(chain = true)
        public static class MuteScheduleRule {
            @JSONField(name = "task_id") private String taskId;
            @JSONField(name = "start_at")
            private String startAt;
            @JSONField(name = "end_at")
            private String endAt;
            private Boolean enabled;
        }

        @Data
        @Accessors(chain = true)
        public static class MuteRecurringRule {
            @JSONField(name = "task_id") private String taskId;
            private List<Integer> weekdays;
            @JSONField(name = "start_time") private String startTime;
            @JSONField(name = "end_time")
            private String endTime;
            private Boolean enabled;
        }
    }

    /** 群成员禁言设置请求。 */
    @Data
    @Accessors(chain = true)
    public static class GroupMuteSettingRequest {
        private List<SetMemberMuteState> members;
    }

    /** 当前成员禁言状态。 */
    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class MemberMuteState extends Member {
        @JSONField(name = "mute_expire_at")
        private String muteExpireAt;
    }
}
