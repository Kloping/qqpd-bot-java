package io.github.kloping.qqbot.entities.qqpd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.map.MapUtils;
import io.github.kloping.qqbot.api.OpAble;
import io.github.kloping.qqbot.api.SessionCreator;
import io.github.kloping.qqbot.api.event.BotContent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.utils.BaseUtils;
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
public class Guild implements SessionCreator, OpAble, BotContent {
    private Boolean owner;
    private String joinedAt;
    private String ownerId;
    private String name;
    private String icon;
    private Integer maxMembers;
    private String description;
    private String id;
    private Integer memberCount;

    private String opUserId;

    /**
     * @param uid
     * @return
     */
    @Override
    public Dms create(String uid) {
        if (this.getMember(uid) == null) return null;
        DmsRequest request = new DmsRequest();
        request.setSourceGuildId(Guild.this.id);
        request.setRecipientId(uid);
        return bot.dmsBase.create(request, Channel.SEND_MESSAGE_HEADERS);
    }

    public MemberWithGuildID getMemberWithGuildId(String userId) {
        Member member = getMember(userId);
        JSONObject jo = JSON.parseObject(JSON.toJSONString(member));
        jo.put("guildId", getId());
        MemberWithGuildID w = jo.toJavaObject(MemberWithGuildID.class);
        w.setBot(getBot());
        return w;
    }

    public Member getMember(String userId) {
        Member member = null;
        member = BaseUtils.tryGet(Common.GUILD_MEMBER_TEMP, Guild.this.getId(), userId);
        if (member == null) {
            member = bot.guildBase.getMember(Guild.this.getId(), userId);
            if (member.getNick() == null || member.getRoles() == null || member.getUser() == null) return null;
            MapUtils.append(Common.GUILD_MEMBER_TEMP, Guild.this.getId(), userId, member);
        }
        return member;
    }

    public Member setMember(Member member) {
        String uid = member.getUser().getId();
        Member m0 = getMember(uid);
        MapUtils.append(Common.GUILD_MEMBER_TEMP, Guild.this.getId(), uid, member);
        return m0;
    }

    private synchronized void channelInit() {
        if (!Common.GUILD_CHANNEL_TEMP.containsKey(id)) {
            Map<String, Channel> map = new HashMap<>();
            Channel[] channels = bot.guildBase.getChannels(id);
            for (Channel channel : channels) {
                map.put(channel.getId(), channel);
            }
            Common.GUILD_CHANNEL_TEMP.put(id, map);
        }
    }

    public List<Channel> channels() {
        if (!Common.GUILD_CHANNEL_TEMP.containsKey(id)) {
            channelInit();
        }
        return new ArrayList<>(Common.GUILD_CHANNEL_TEMP.get(id).values());
    }

    public Map<String, Channel> channelMap() {
        if (!Common.GUILD_CHANNEL_TEMP.containsKey(id)) {
            channelInit();
        }
        return Common.GUILD_CHANNEL_TEMP.get(id);
    }

    @JSONField(serialize = false, deserialize = false)
    private Bot bot;

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}
