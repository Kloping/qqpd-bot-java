package io.github.kloping.qqbot.entities.qqpd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.map.MapUtils;
import io.github.kloping.qqbot.api.BotContent;
import io.github.kloping.qqbot.api.OpAble;
import io.github.kloping.qqbot.api.SessionCreator;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.ChannelData;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
@EqualsAndHashCode(callSuper = false)
public class Guild implements SessionCreator, OpAble, BotContent {
    private Boolean owner;
    private String joinedAt;
    private String ownerId;
    private String name;
    private String icon;
    private Integer maxMembers;
    private String description;
    @Getter
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
        member.setGuild(this);
        w.setGuild(this);
        return w;
    }

    public Member getMember(String userId) {
        Member member = null;
        member = BaseUtils.tryGet(Common.GUILD_MEMBER_TEMP, Guild.this.getId(), userId);
        if (member == null) {
            member = bot.guildBase.getMember(Guild.this.getId(), userId);
            member.setGuild(this);
            if (member.getNick() == null || member.getRoles() == null || member.getUser() == null) return null;
            MapUtils.append(Common.GUILD_MEMBER_TEMP, Guild.this.getId(), userId, member);
        }
        return member;
    }

    public Member setMember(Member member) {
        String uid = member.getUser().getId();
        Member m0 = getMember(uid);
        if (m0 == null) m0 = member;
        member.setGuild(this);
        MapUtils.append(Common.GUILD_MEMBER_TEMP, Guild.this.getId(), uid, member);
        return m0;
    }

    private synchronized void channelInit() {
        if (!Common.GUILD_CHANNEL_TEMP.containsKey(id)) {
            Map<String, Channel> map = new HashMap<>();
            Channel[] channels = bot.guildBase.getChannels(id);
            if (channels == null || channels.length == 0)
                throw new RuntimeException(String.format("Guild(%s) channels list Initialization failed", getName()));
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

    public Channel getChannel(String cid) {
        Map<String, Channel> cm = null;
        if ((cm = Common.GUILD_CHANNEL_TEMP.getOrDefault(id, Common.EMPTY_CHANNEL_MAP)).containsKey(cid)) return cm.get(cid);
        Channel channel = getBot().channelBase.getChannel(cid);
        MapUtils.append(Common.GUILD_CHANNEL_TEMP, Guild.this.getId(), cid, channel, HashMap.class);
        return channel;
    }

    @Getter
    @JSONField(serialize = false, deserialize = false)
    private Bot bot;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * 创建一个子频道 ## 仅私域
     *
     * @param data
     */
    public Channel create(ChannelData data) {
        Channel channel = getBot().guildBase.create(Channel.SEND_MESSAGE_HEADERS, Guild.this.getId(), data);
        return channel;
    }
}
