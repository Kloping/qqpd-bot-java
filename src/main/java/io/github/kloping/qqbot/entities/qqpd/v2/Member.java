package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupMuteSetting;
import io.github.kloping.qqbot.entities.qqpd.v2.data.SetMemberMuteState;
import io.github.kloping.qqbot.http.BaseV2;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

/**
 * @author github.kloping
 */
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Member extends Contact implements SenderAndCidMidGetter, SenderV2 {
    private transient Group group;
    @JSONField(name = "member_openid")
    private String memberOpenid;
    private String username;
    @JSONField(name = "member_role")
    private String memberRole;
    @JSONField(name = "joined_at")
    private String joinedAt;
    @JSONField(name = "union_openid")
    private String unionOpenid;
    /**
     * 成员是否为机器人账号；getBot() 仍保留为机器人客户端上下文。
     */
    @JSONField(name = "bot")
    private Boolean botAccount;

    public Member() {
    }

    public Member(JSONObject mate) {
        super(mate);
        this.setId(this.getMeta().getString("id"));
        this.setOpenid(this.getMeta().getString("member_openid"));
        this.setMemberOpenid(this.getOpenid());
    }

    @Override
    public Result<V2Result> send(String text) {
        V2MsgData data = new V2MsgData().setContent(text);
        return new Result<V2Result>(bot.userBaseV2.send(getOpenid(), JSON.toJSONString(data), Channel.SEND_MESSAGE_HEADERS));
    }

    @Override
    public Result<V2Result> send(String text, RawMessage message) {
        return message.send(text);
    }

    @Override
    public Result send(SendAble msg) {
        return msg.send(this);
    }

    @Override
    public String getCid() {
        return getOpenid();
    }

    @Override
    public EnvType getEnvType() {
        return EnvType.GROUP_USER;
    }

    @JSONField(deserialize = false, serialize = false)
    @Override
    public BaseV2 getV2() {
        return getBot().userBaseV2;
    }

    /**
     * 设置成员禁言，时长单位为秒。
     */
    public void mute(Mute operation, long seconds) {
        if (group == null) throw new IllegalStateException("Member 未关联群上下文，无法设置禁言");
        if (operation == null) throw new IllegalArgumentException("禁言操作不能为空");
        String expireAt = OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(seconds).toString();
        group.setMuteSetting(new GroupMuteSetting.GroupMuteSettingRequest().setMembers(Collections.singletonList(
                new SetMemberMuteState().setOp(operation.getValue()).setMemberOpenid(getOpenid()).setMuteExpireAt(expireAt))));
    }

    /**
     * 兼容未导入 v2.Mute 的调用方。
     */
    public void mute(Enum<?> operation, long seconds) {
        if (operation == null) throw new IllegalArgumentException("禁言操作不能为空");
        String name = operation.name();
        mute(Mute.valueOf(name), seconds);
    }

    /**
     * 解除成员禁言。
     */
    public void muteDel() {
        if (group == null) throw new IllegalStateException("Member 未关联群上下文，无法解除禁言");
        group.setMuteSetting(new GroupMuteSetting.GroupMuteSettingRequest().setMembers(Collections.singletonList(
                new SetMemberMuteState().setOp("del").setMemberOpenid(getOpenid()).setMuteExpireAt(""))));
    }

}
