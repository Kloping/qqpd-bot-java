package io.github.kloping.qqbot.entities;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.User;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinApprovalStrategyList;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.*;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.Entity;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
@Entity
public class Bot {
    @AutoStand
    public InterActionBase interActionBase;

    @AutoStand
    public GuildBase guildBase;

    @AutoStand
    public UserBase userBase;

    @AutoStand
    public ChannelBase channelBase;

    @AutoStand
    public DmsBase dmsBase;

    @AutoStand
    public MessageBase messageBase;

    @AutoStand
    public MemberBase memberBase;

    @AutoStand
    public GroupBaseV2 groupBaseV2;

    @AutoStand
    public UserBaseV2 userBaseV2;

    @AutoStand
    public AuthV2Base authV2Base;

    @Getter
    @AutoStand
    Starter.Config config;

    private User user;

    private Map<String, Guild> guildMap = new HashMap<>();

    private void tryLoadGuilds() {
        if (guildMap.isEmpty()) {
            user = userBase.botInfo();
            for (Guild guild : guildBase.getGuilds()) {
                guild.setBot(this);
                guildMap.put(guild.getId(), guild);
            }
        }
    }

    public synchronized Guild getGuild(String id) {
        tryLoadGuilds();
        if (!guildMap.containsKey(id)) {
            Guild guild = guildBase.getGuild(id);
            if (guild != null) setGuild(guild);
        }
        return guildMap.get(id);
    }

    public Guild setGuild(Guild guild) {
        guildMap.put(guild.getId(), guild);
        return guildMap.get(guild.getId());
    }

    public Guild delGuild(Guild guild) {
        guildMap.remove(guild.getId());
        return guild;
    }

    public Collection<Guild> guilds() {
        tryLoadGuilds();
        return guildMap.values();
    }

    public synchronized User getInfo() {
        if (user == null) {
            user = userBase.botInfo();
        }
        return user;
    }

    public String getId() {
        return getInfo().getId();
    }

    /**
     * 主动向指定群发送消息。
     *
     * <p>该方法使用群 OpenID 作为目标标识，并复用 {@link Group#send(SendAble)}
     * 的消息编码逻辑，支持文本、图片及其他 {@link SendAble} 消息类型。</p>
     *
     * @param groupId 群 OpenID
     * @param message 要发送的消息
     * @return QQ 开放平台返回的消息结果
     * @throws IllegalArgumentException 当群 OpenID 或消息为空时抛出
     */
    public Result sendMessage(String groupId, SendAble message) {
        if (groupId == null || groupId.trim().isEmpty()) {
            throw new IllegalArgumentException("群 OpenID 不能为空");
        }
        if (message == null) {
            throw new IllegalArgumentException("消息不能为空");
        }
        JSONObject meta = new JSONObject();
        meta.put("group_id", groupId);
        meta.put("group_openid", groupId);
        Group group = new Group(meta);
        group.setBot(this);
        return group.send(message);
    }

    /**
     * 查询当前生效中的入群自动审批策略列表。
     *
     * @param cursor 分页游标，首次请求可传空
     * @param limit 单页数量，默认 20，最大 50
     * @return 入群自动审批策略分页结果
     */
    public JoinApprovalStrategyList getJoinApprovalStrategyList(String cursor, Integer limit) {
        return groupBaseV2.getJoinApprovalStrategyList(cursor, limit);
    }

    /**
     * 查询当前生效中的入群自动审批策略的第一页。
     *
     * @return 入群自动审批策略分页结果
     */
    public JoinApprovalStrategyList getJoinApprovalStrategyList() {
        return getJoinApprovalStrategyList(null, null);
    }
}
