package io.github.kloping.qqbot.api.qqpd;

import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.qqpd.interfaces.SessionCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class Guild implements SessionCreator {
    private Boolean owner;
    private String joinedAt;
    private String ownerId;
    private String name;
    private String icon;
    private Integer maxMembers;
    private String description;
    private String id;
    private Integer memberCount;

    /**
     * 目前尚存在一些问题
     *
     * @param uid
     * @return
     */
    @Override
    public Dms create(String uid) {
        if (!memberMap().containsKey(uid)) return null;
        DmsRequest request = new DmsRequest();
        request.setSourceGuildId(Guild.this.id);
        request.setRecipientId(uid);
        return Resource.dmsBase.create(request, Channel.MAP);
    }

    private synchronized void initTemp0() {
        if (!Common.GUILD_MEMBER_TEMP.containsKey(id)) {
            Map<String, Member> map = new HashMap<>();
            Member[] members = Resource.guildBase.getMembers(id, 100);
            for (Member member : members) {
                map.put(member.getUser().getId(), member);
            }
            Common.GUILD_MEMBER_TEMP.put(id, map);
        }
    }

    private synchronized void initTemp1() {
        if (!Common.GUILD_CHANNEL_TEMP.containsKey(id)) {
            Map<String, Channel> map = new HashMap<>();
            Channel[] channels = Resource.guildBase.getChannels(id);
            for (Channel channel : channels) {
                map.put(channel.getId(), channel);
            }
            Common.GUILD_CHANNEL_TEMP.put(id, map);
        }
    }

    public List<Member> members() {
        if (!Common.GUILD_MEMBER_TEMP.containsKey(id)) {
            initTemp0();
        }
        return new ArrayList<>(Common.GUILD_MEMBER_TEMP.get(id).values());
    }

    public Map<String, Member> memberMap() {
        if (!Common.GUILD_MEMBER_TEMP.containsKey(id)) {
            initTemp0();
        }
        return Common.GUILD_MEMBER_TEMP.get(id);
    }

    public List<Channel> channels() {
        if (!Common.GUILD_CHANNEL_TEMP.containsKey(id)) {
            initTemp1();
        }
        return new ArrayList<>(Common.GUILD_CHANNEL_TEMP.get(id).values());
    }

    public Map<String, Channel> channelMap() {
        if (!Common.GUILD_CHANNEL_TEMP.containsKey(id)) {
            initTemp1();
        }
        return Common.GUILD_CHANNEL_TEMP.get(id);
    }
}
