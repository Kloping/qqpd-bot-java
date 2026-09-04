package io.github.kloping.qqbot.entities.qqpd.v2.data;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 机器人在群内的状态
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>member_openid</td> <td>string</td> <td>机器人的 openid</td></tr>
 * <tr><td>joined_at</td> <td>string</td> <td>入群时间戳（RFC3339格式）</td></tr>
 * <tr><td>allow_proactive_msg</td> <td>boolean</td> <td>是否接收主动推送。true: 接受主动推送</td></tr>
 * <tr><td>recv_msg_setting</td> <td>string</td> <td>接受消息的类型：all、only_mention、mention_and_context</td></tr>
 * <tr><td>member_role</td> <td>string</td> <td>群成员角色：member-普通成员，owner-群主，admin-管理员</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class GroupBotState {
    private String memberOpenid;
    private String joinedAt;
    private Boolean allowProactiveMsg;
    private String recvMsgSetting;
    private String memberRole;
}
