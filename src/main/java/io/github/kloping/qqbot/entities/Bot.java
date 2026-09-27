package io.github.kloping.qqbot.entities;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Start0;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.User;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import io.github.kloping.qqbot.entities.qqpd.v2.data.JoinApprovalStrategyList;
import io.github.kloping.qqbot.entities.qqpd.v2.data.Menu;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MenuData;
import io.github.kloping.qqbot.entities.qqpd.v2.data.Panel;
import io.github.kloping.qqbot.entities.qqpd.v2.data.PanelDetail;
import io.github.kloping.qqbot.entities.qqpd.v2.data.PanelRecordPage;
import io.github.kloping.qqbot.entities.qqpd.v2.data.VersionData;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.*;
import io.github.kloping.qqbot.utils.HttpUtils;
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

    @AutoStand
    public MenuPanelBase menuPanelBase;

    @AutoStand
    public Start0 start0;

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

    /**
     * 查询全局自定义菜单。
     *
     * @return 当前菜单配置，未设置过菜单时 {@code menu} 为空
     */
    public MenuData getMenu() {
        return menuPanelBase.getMenu();
    }

    /**
     * 修改全局自定义菜单，会覆盖原有完整配置。
     *
     * @param menu 菜单配置
     * @return 修改后的菜单版本号
     */
    public VersionData setMenu(Menu menu) {
        return menuPanelBase.setMenu(new MenuData().setMenu(menu));
    }

    /**
     * 分页拉取指定场景下已生效的指令面板列表。
     *
     * @param scope  生效场景，见 {@link Panel#SCOPE_C2C} 等
     * @param cursor 分页游标，首次请求可传空
     * @param limit  每页条数，默认 20，最大 50
     * @return 面板列表分页结果
     */
    public PanelRecordPage getPanels(String scope, String cursor, Integer limit) {
        return menuPanelBase.getPanels(scope, cursor, limit);
    }

    /**
     * 查询指定场景指令面板列表的第一页。
     *
     * @param scope 生效场景，见 {@link Panel#SCOPE_C2C} 等
     * @return 面板列表分页结果
     */
    public PanelRecordPage getPanels(String scope) {
        return getPanels(scope, null, null);
    }

    /**
     * 创建指令面板。
     *
     * @param request 创建请求
     * @return 新创建的面板 ID
     */
    public Panel.Created createPanel(Panel.CreateRequest request) {
        return menuPanelBase.createPanel(request);
    }

    /**
     * 查询指定指令面板的完整配置详情。
     *
     * @param panelId 面板 ID
     * @return 面板详情
     */
    public PanelDetail getPanel(String panelId) {
        return menuPanelBase.getPanel(panelId);
    }

    /**
     * 修改指定指令面板的配置内容，不影响已关联的用户/群列表。
     *
     * @param panelId 面板 ID
     * @param panel   面板配置
     * @return 修改后的面板版本号
     */
    public VersionData updatePanel(String panelId, Panel panel) {
        return menuPanelBase.updatePanel(panelId, new Panel.UpdateRequest().setPanel(panel));
    }

    /**
     * 修改指定指令面板关联的用户或群。
     *
     * @param panelId 面板 ID
     * @param request 关联对象操作，channel 与 dm 场景不支持
     */
    public void updatePanelTarget(String panelId, Panel.TargetRequest request) {
        menuPanelBase.updatePanelTarget(panelId, request);
    }

    /**
     * 删除指定指令面板，删除后不再对任何用户或群生效。
     *
     * <p>底层 {@code @HttpClient} 代理无法正确发送 DELETE 请求（详见 {@link MenuPanelBase}），
     * 故此处直接发送。</p>
     *
     * @param panelId 面板 ID
     */
    public void deletePanel(String panelId) {
        if (panelId == null || panelId.trim().isEmpty()) {
            throw new IllegalArgumentException("面板 ID 不能为空");
        }
        HttpUtils.delete(start0.getNet() + "v2/panels/" + panelId, start0.getHeaders());
    }
}
