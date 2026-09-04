package io.github.kloping.qqbot.http;

import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupBotState;
import io.github.kloping.qqbot.entities.qqpd.v2.data.GroupInfo;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinApproval;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinRequestList;
import io.github.kloping.qqbot.http.data.V2Result;
import io.github.kloping.spt.annotations.http.*;

import java.util.Map;

/**
 * <table><tr><th colspan="2">基本</th></tr> <tr><td>HTTP URL</td> <td>/v2/groups/{group_openid}/messages</td></tr> <tr><td>HTTP Method</td> <td>POST</td></tr></table>
 * <hr>
 * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>group_openid</td> <td>string</td> <td>是</td> <td>群聊的 openid</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@HttpClient(Starter.NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface GroupBaseV2 extends BaseV2 {
    /**
     * 获取群基本信息
     *
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>说明</strong></th></tr></thead>
     * <tbody><tr><td>group_openid</td> <td>string</td> <td>群 OpenID</td></tr>
     * <tr><td>group_name</td> <td>string</td> <td>群名称</td></tr>
     * <tr><td>group_finger_memo</td> <td>string</td> <td>群简介</td></tr>
     * <tr><td>group_class_text</td> <td>string</td> <td>群分类</td></tr>
     * <tr><td>group_tags</td> <td>[]string</td> <td>群标签列表</td></tr>
     * <tr><td>group_member_num</td> <td>integer</td> <td>群成员人数</td></tr></tbody></table>
     *
     * @param gid 群 OpenID
     * @return 群基本信息
     */
    @GetPath("/v2/groups/{group_openid}/info")
    GroupInfo getGroupInfo(@PathValue("group_openid") String gid);

    /**
     * 获取机器人在群内的状态
     *
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>说明</strong></th></tr></thead>
     * <tbody><tr><td>member_openid</td> <td>string</td> <td>机器人的 openid</td></tr>
     * <tr><td>joined_at</td> <td>string</td> <td>入群时间戳（RFC3339格式）</td></tr>
     * <tr><td>allow_proactive_msg</td> <td>boolean</td> <td>是否接收主动推送。true: 接受主动推送</td></tr>
     * <tr><td>recv_msg_setting</td> <td>string</td> <td>接受消息的类型：all、only_mention、mention_and_context</td></tr>
     * <tr><td>member_role</td> <td>string</td> <td>群成员角色：member-普通成员，owner-群主，admin-管理员</td></tr></tbody></table>
     *
     * @param gid 群 OpenID
     * @return 机器人在群内的状态
     */
    @GetPath("/v2/groups/{group_openid}/bot_state")
    GroupBotState getBotState(@PathValue("group_openid") String gid);

    /**
     * 拉取入群申请列表。
     *
     * @param gid 群 OpenID
     * @param cursor 分页游标，首次请求可传空
     * @param limit 单页数量，最大 50
     * @return 入群申请分页结果
     */
    @GetPath("/v2/groups/{group_openid}/join_request_list")
    JoinRequestList getJoinRequestList(@PathValue("group_openid") String gid,
                                       @ParamName("cursor") @DefaultValue("") String cursor,
                                       @ParamName("limit") @DefaultValue("20") Integer limit);

    /**
     * 审批入群申请。
     *
     * @param gid 群 OpenID
     * @param memberOpenid 申请人 OpenID
     * @param approval 审批参数
     */
    @PostPath("/v2/groups/{group_openid}/approval_join_request/{member_openid}")
    void approvalJoinRequest(@PathValue("group_openid") String gid,
                             @PathValue("member_openid") String memberOpenid,
                             @RequestBody(type = RequestBody.type.json) JoinApproval approval);

    /**
     * 发送群聊消息
     *
     * @param gid
     * @param body
     * @param headers
     * @return msg
     */
    @PostPath("/v2/groups/{group_openid}/messages")
    V2Result send(@PathValue("group_openid") String gid, @RequestBody(type = RequestBody.type.json) String body, @Headers Map<String, String> headers);

    /**
     * 发送群聊媒体
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>file_type</td> <td>int</td> <td>是</td> <td>媒体类型：1 图片，2 视频，3 语音，4 文件（暂不开放）<br>资源格式要求<br>图片：png/jpg，视频：mp4，语音：silk</td></tr> <tr><td>url</td> <td>string</td> <td>是</td> <td>需要发送媒体资源的url</td></tr> <tr><td>srv_send_msg</td> <td>bool</td> <td>是</td> <td>设置 true 会直接发送消息到目标端，且会占用<code>主动消息频次</code></td></tr> <tr><td>file_data</td> <td></td> <td>否</td> <td>【暂未支持】</td></tr></tbody></table>
     *
     * @param gid
     * @param body
     * @param headers
     * @return 文件id
     */
    @PostPath("/v2/groups/{group_openid}/files")
    V2Result sendFile(@PathValue("group_openid") String gid, @RequestBody(type = io.github.kloping.spt.annotations.http.RequestBody.type.json) String body, @Headers Map<String, String> headers);
}
