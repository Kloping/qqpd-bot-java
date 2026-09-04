package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupBotState;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupInfo;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinApproval;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinRequestList;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupMemberList;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupMuteSetting;
import io.github.kloping.qqbot.entities.qqpd.v2.data.SetMemberMuteState;
import io.github.kloping.qqbot.entities.qqpd.v2.data.BatchRemoveMembersRequest;
import io.github.kloping.qqbot.entities.qqpd.v2.data.BatchRemoveMembersResult;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MemberBlacklist;
import io.github.kloping.spt.util.Judge;;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.entities.ex.FileMsg;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.http.BaseV2;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 * @author github.kloping
 */
@Getter
@Accessors(chain = true)
public class Group extends Contact implements SenderV2 {

    public Group(JSONObject mate) {
        super(mate);
        this.setId(getMeta().getString("group_id"));
        this.setOpenid(getMeta().getString("group_openid"));
    }

    /**
     * 获取当前群的基本信息
     *
     * @return 群基本信息
     */
    public GroupInfo getInfo() {
        return bot.groupBaseV2.getGroupInfo(getOpenid());
    }

    /**
     * 获取机器人在当前群内的状态
     *
     * @return 机器人群内状态
     */
    public GroupBotState getBotState() {
        return bot.groupBaseV2.getBotState(getOpenid());
    }

    /**
     * 分页获取当前群成员列表。
     *
     * @param cursor 分页游标，首次请求可传空字符串
     * @return 群成员分页结果
     */
    public GroupMemberList getMembers(String cursor) {
        return bot.groupBaseV2.getMembers(getOpenid(), cursor);
    }

    /**
     * 该能力正在内邀接入中，敬请期待
     * <hr/>
     * 获取当前群第一页成员列表。
     *
     * @return 群成员分页结果
     */
    public GroupMemberList getMembers() {
        return getMembers("");
    }

    /**
     * 该能力正在内邀接入中，敬请期待。
     * <hr/>
     * 批量移除当前群成员，单次最多 20 个。
     */
    public BatchRemoveMembersResult batchRemoveMembers(List<String> memberOpenids, boolean addToMemberBlacklist) {
        return bot.groupBaseV2.batchRemoveMembers(getOpenid(), new BatchRemoveMembersRequest()
                .setMemberOpenids(memberOpenids)
                .setAddToMemberBlacklist(addToMemberBlacklist));
    }

    /**
     * 该能力正在内邀接入中，敬请期待。
     * <hr/>
     * 批量移除当前群成员，默认不加入群黑名单。
     */
    public BatchRemoveMembersResult batchRemoveMembers(List<String> memberOpenids) {
        return batchRemoveMembers(memberOpenids, false);
    }

    /**
     * 该能力正在内邀接入中，敬请期待。
     * <hr/>
     * 查询当前群黑名单。
     */
    public MemberBlacklist getMemberBlacklist(String cursor, Integer limit) {
        MemberBlacklist result = bot.groupBaseV2.getMemberBlacklist(getOpenid(), cursor, limit);
        if (result != null && result.getUsers() != null) {
            result.getUsers().forEach(member -> member.setGroup(this));
        }
        return result;
    }

    /**
     * 该能力正在内邀接入中，敬请期待。
     * <hr/>
     * 获取当前群第一页黑名单成员。
     */
    public MemberBlacklist getMemberBlacklist() {
        return getMemberBlacklist("", 20);
    }

    /**
     * 该能力正在内邀接入中，敬请期待
     * <hr/>
     * 获取指定群成员的详细信息。
     * @param memberOpenid 成员 OpenID
     * @return 群成员信息
     */
    public Member getMember(String memberOpenid) {
        Member member = bot.groupBaseV2.getMember(getOpenid(), memberOpenid);
        member.setGroup(this);
        return member;
    }

    /**
     * 查询当前群的全员禁言规则及成员禁言列表。
     *
     * @return 群禁言状态
     */
    public GroupMuteSetting getMuteSetting() {
        return bot.groupBaseV2.getMuteSetting(getOpenid());
    }

    /**
     * 设置当前群成员级禁言状态。
     *
     * @param request 禁言操作请求，支持 add、update、del
     */
    public void setMuteSetting(GroupMuteSetting.GroupMuteSettingRequest request) {
        bot.groupBaseV2.setMuteSetting(getOpenid(), request);
    }

    /**
     * 设置指定群成员的禁言状态，时长单位为秒。
     *
     * @param memberOpenid 成员 OpenID
     * @param operation 禁言操作类型
     * @param seconds 禁言时长（秒）
     */
    public void muteMember(String memberOpenid, Mute operation, long seconds) {
        if (operation == null) throw new IllegalArgumentException("禁言操作不能为空");
        String expireAt = OffsetDateTime.now(ZoneOffset.UTC).plusSeconds(seconds).toString();
        setMuteSetting(new GroupMuteSetting.GroupMuteSettingRequest().setMembers(Collections.singletonList(
                new SetMemberMuteState().setOp(operation.getValue())
                        .setMemberOpenid(memberOpenid)
                        .setMuteExpireAt(expireAt))));
    }

    /**
     * 解除指定群成员的禁言状态。
     *
     * @param memberOpenid 成员 OpenID
     */
    public void unmuteMember(String memberOpenid) {
        setMuteSetting(new GroupMuteSetting.GroupMuteSettingRequest().setMembers(Collections.singletonList(
                new SetMemberMuteState().setOp("del")
                        .setMemberOpenid(memberOpenid)
                        .setMuteExpireAt(""))));
    }


    /**
     * 拉取当前群的入群申请列表。
     *
     * @param cursor 分页游标，首次请求可传空
     * @param limit 单页数量，最大 50
     * @return 入群申请分页结果
     */
    public JoinRequestList getJoinRequestList(String cursor, Integer limit) {
        JoinRequestList result = bot.groupBaseV2.getJoinRequestList(getOpenid(), cursor, limit);
        if (result != null && result.getList() != null) {
            result.getList().forEach(request -> request.setGroup(this));
        }
        return result;
    }

    /**
     * 获取当前群第一页入群申请。
     *
     * @return 入群申请分页结果
     */
    public JoinRequestList getJoinRequestList() {
        return getJoinRequestList(null, null);
    }

    /**
     * 审批当前群的入群申请。
     *
     * @param memberOpenid 申请人 OpenID
     * @param approval 审批参数
     */
    public void approvalJoinRequest(String memberOpenid, JoinApproval approval) {
        bot.groupBaseV2.approvalJoinRequest(getOpenid(), memberOpenid, approval);
    }

    @Override
    public Result<V2Result> send(String text) {
        V2MsgData data = new V2MsgData().setContent(text);
        return new Result<V2Result>(bot.groupBaseV2.send(getOpenid(), JSON.toJSONString(data), Channel.SEND_MESSAGE_HEADERS));
    }

    @Override
    public Result<V2Result> send(String text, RawMessage message) {
        return message.send(text);
    }

    private V2Result sendFileMsg(FileMsg msg) {
        RawMessage.filePrepare(msg, bot);
        V2Result result = null;
        if (Judge.isNotEmpty(msg.getUrl())) {
            result = bot.groupBaseV2.sendFile(getOpenid(), String.format("{\"file_type\": %s,\"url\": \"%s\",\"srv_send_msg\": false}", msg.getFile_type(), msg.getUrl()), Channel.SEND_MESSAGE_HEADERS);
        } else {
            result = bot.groupBaseV2.sendFile(getCid(), String.format("{\"file_type\": %s,\"file_data\": \"%s\",\"srv_send_msg\": false}", msg.getFile_type(), Base64.getEncoder().encodeToString(msg.getBytes())), Channel.SEND_MESSAGE_HEADERS);
        }
        result.logFileInfo(msg);
        V2MsgData data = new V2MsgData();
        data.setMedia(new V2MsgData.Media(result.getFile_info()));
        return bot.groupBaseV2.send(getOpenid(), data.toString(), SEND_MESSAGE_HEADERS);
    }

    @Override
    public Result send(SendAble msg) {
        if (msg instanceof Image) {
            return new Result(sendFileMsg((Image) msg));
        } else return msg.send(this);
    }

    @Override
    public String getCid() {
        return getOpenid();
    }

    @Override
    public EnvType getEnvType() {
        return EnvType.GROUP;
    }

    @Override
    public BaseV2 getV2() {
        return getBot().groupBaseV2;
    }
}
