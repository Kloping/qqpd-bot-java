package io.github.kloping.qqbot.http;

import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MenuData;
import io.github.kloping.qqbot.entities.qqpd.v2.data.Panel;
import io.github.kloping.qqbot.entities.qqpd.v2.data.PanelDetail;
import io.github.kloping.qqbot.entities.qqpd.v2.data.PanelRecordPage;
import io.github.kloping.qqbot.entities.qqpd.v2.data.VersionData;
import io.github.kloping.spt.annotations.http.DefaultValue;
import io.github.kloping.spt.annotations.http.GetPath;
import io.github.kloping.spt.annotations.http.Headers;
import io.github.kloping.spt.annotations.http.HttpClient;
import io.github.kloping.spt.annotations.http.ParamName;
import io.github.kloping.spt.annotations.http.PathValue;
import io.github.kloping.spt.annotations.http.PostPath;
import io.github.kloping.spt.annotations.http.RequestBody;
import io.github.kloping.spt.annotations.http.RequestPath;
import org.jsoup.Connection;

/**
 * 自定义菜单与指令面板 HTTP 接口。
 *
 * <p>官方文档：<a href="https://bot.q.qq.com/wiki/develop/api-v2/server-inter/menu-panel/">自定义菜单与指令面板</a></p>
 *
 *
 * @author github.kloping
 */
@HttpClient(Starter.NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface MenuPanelBase {
    /**
     * 查询全局自定义菜单。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/menu</td></tr> <tr><td>HTTP Method</td> <td>GET</td></tr></table>
     *
     * @return 当前菜单配置，未设置过菜单时 {@code menu} 为空
     */
    @GetPath("/v2/menu")
    MenuData getMenu();

    /**
     * 修改全局自定义菜单，传入后会覆盖原有的完整菜单配置。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/menu</td></tr> <tr><td>HTTP Method</td> <td>PUT</td></tr></table>
     *
     * @param menu 菜单配置，仅需携带 {@link MenuData#getMenu()} 字段
     * @return 修改后的菜单版本号
     */
    @RequestPath(value = "/v2/menu", method = Connection.Method.PUT)
    VersionData setMenu(@RequestBody(type = RequestBody.type.json) MenuData menu);

    /**
     * 分页拉取指定场景下已生效的指令面板列表，按设置时间倒序排列。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/panels</td></tr> <tr><td>HTTP Method</td> <td>GET</td></tr></table>
     *
     * @param scope  生效场景：{@link Panel#SCOPE_C2C} 等，必填
     * @param cursor 分页游标，首次请求可传空
     * @param limit  每页条数，默认 20，最大 50
     * @return 面板列表分页结果
     */
    @GetPath("/v2/panels")
    PanelRecordPage getPanels(@ParamName("scope") String scope,
                              @ParamName("cursor") @DefaultValue("") String cursor,
                              @ParamName("limit") @DefaultValue("20") Integer limit);

    /**
     * 创建指令面板。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/panels</td></tr> <tr><td>HTTP Method</td> <td>POST</td></tr></table>
     *
     * @param request 创建请求，一个机器人最多创建 20 个指令面板
     * @return 新创建的面板 ID
     */
    @PostPath("/v2/panels")
    Panel.Created createPanel(@RequestBody(type = RequestBody.type.json) Panel.CreateRequest request);

    /**
     * 查询指定指令面板的完整配置详情。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/panels/{panel_id}</td></tr> <tr><td>HTTP Method</td> <td>GET</td></tr></table>
     *
     * @param panelId 面板 ID
     * @return 面板详情
     */
    @GetPath("/v2/panels/{panel_id}")
    PanelDetail getPanel(@PathValue("panel_id") String panelId);

    /**
     * 修改指定指令面板的配置内容，不影响已关联的用户/群列表。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/panels/{panel_id}</td></tr> <tr><td>HTTP Method</td> <td>PUT</td></tr></table>
     *
     * @param panelId 面板 ID
     * @param request 面板配置
     * @return 修改后的面板版本号
     */
    @RequestPath(value = "/v2/panels/{panel_id}", method = Connection.Method.PUT)
    VersionData updatePanel(@PathValue("panel_id") String panelId,
                            @RequestBody(type = RequestBody.type.json) Panel.UpdateRequest request);

    /**
     * 修改指定指令面板关联的用户或群。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/panels/{panel_id}/target</td></tr> <tr><td>HTTP Method</td> <td>PUT</td></tr></table>
     *
     * @param panelId 面板 ID
     * @param request 关联对象操作，channel 与 dm 场景不支持
     */
    @RequestPath(value = "/v2/panels/{panel_id}/target", method = Connection.Method.PUT)
    void updatePanelTarget(@PathValue("panel_id") String panelId,
                           @RequestBody(type = RequestBody.type.json) Panel.TargetRequest request);

    /**
     * 删除指定指令面板，删除后不再对任何用户或群生效。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/panels/{panel_id}</td></tr> <tr><td>HTTP Method</td> <td>DELETE</td></tr></table>
     *
     * @param panelId 面板 ID
     */
    @RequestPath(value = "/v2/panels/{panel_id}", method = Connection.Method.DELETE)
    void deletePanel(@PathValue("panel_id") String panelId, @RequestBody(type = RequestBody.type.toString) String empty);
}
