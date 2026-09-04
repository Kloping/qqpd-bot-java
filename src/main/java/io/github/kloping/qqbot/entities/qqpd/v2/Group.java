package io.github.kloping.qqbot.entities.qqpd.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupBotState;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupInfo;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinApproval;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinRequestList;
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
