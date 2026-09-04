package io.github.kloping.qqbot.entities.qqpd.v2.data;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 群基本信息
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>group_openid</td> <td>string</td> <td>群 OpenID</td></tr>
 * <tr><td>group_name</td> <td>string</td> <td>群名称</td></tr>
 * <tr><td>group_finger_memo</td> <td>string</td> <td>群简介</td></tr>
 * <tr><td>group_class_text</td> <td>string</td> <td>群分类</td></tr>
 * <tr><td>group_tags</td> <td>[]string</td> <td>群标签列表</td></tr>
 * <tr><td>group_member_num</td> <td>integer</td> <td>群成员人数</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class GroupInfo {
    private String groupOpenid;
    private String groupName;
    private String groupFingerMemo;
    private String groupClassText;
    private List<String> groupTags;
    private Integer groupMemberNum;
}
