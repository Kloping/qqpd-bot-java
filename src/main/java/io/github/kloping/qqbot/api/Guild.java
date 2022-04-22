package io.github.kloping.qqbot.api;

import io.github.kloping.MySpringTool.StarterApplication;
import io.github.kloping.qqbot.http.GuildBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h3 id="guild"><a href="#guild" class="header-anchor">#</a> Guild</h3>
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>频道ID</td></tr> <tr><td>name</td> <td>string</td> <td>频道名称</td></tr> <tr><td>icon</td> <td>string</td> <td>频道头像地址</td></tr> <tr><td>owner_id</td> <td>string</td> <td>创建人用户ID</td></tr> <tr><td>owner</td> <td>bool</td> <td>当前人是否是创建人</td></tr> <tr><td>member_count</td> <td>int</td> <td>成员数</td></tr> <tr><td>max_members</td> <td>int</td> <td>最大成员数</td></tr> <tr><td>description</td> <td>string</td> <td>描述</td></tr> <tr><td>joined_at</td> <td>string</td> <td>加入时间</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Guild {
    private Boolean owner;
    private String joined_at;
    private String owner_id;
    private String name;
    private String icon;
    private Integer max_members;
    private String description;
    private String id;
    private Integer member_count;

    public List<Member> members() {
        Member[] members = StarterApplication.Setting.INSTANCE.getContextManager().getContextEntity(GuildBase.class).getMembers(id, 100);
        List<Member> memberList = new ArrayList<>(Arrays.asList(members));
        return memberList;
    }

    public List<Channel> channels() {
        Channel[] channels = StarterApplication.Setting.INSTANCE.getContextManager().getContextEntity(GuildBase.class).getChannels(id);
        return new ArrayList<Channel>(Arrays.asList(channels));
    }
}
