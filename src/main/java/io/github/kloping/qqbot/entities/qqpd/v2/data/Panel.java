package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 指令面板配置内容，同时承载面板的创建、修改、关联对象调整等请求体。
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>items</td> <td>[]PanelItem</td> <td>面板元素列表，最多 20 个</td></tr>
 * <tr><td>remark</td> <td>string</td> <td>面板备注，最多 255 个字符，不对用户展示</td></tr>
 * <tr><td>version</td> <td>integer</td> <td>当前版本号</td></tr></tbody></table>
 *
 * <p>官方文档：<a href="https://bot.q.qq.com/wiki/develop/api-v2/server-inter/menu-panel/">自定义菜单与指令面板</a></p>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class Panel {
    /** 生效场景：单聊。 */
    public static final String SCOPE_C2C = "c2c";
    /** 生效场景：群聊。 */
    public static final String SCOPE_GROUP = "group";
    /** 生效场景：文字子频道。 */
    public static final String SCOPE_CHANNEL = "channel";
    /** 生效场景：频道私信。 */
    public static final String SCOPE_DM = "dm";

    /** 作用范围：对该场景下所有用户/群生效。 */
    public static final String TARGET_ALL = "all";
    /** 作用范围：仅对指定用户/群生效。 */
    public static final String TARGET_SPECIFIC = "specific";

    /** 面板元素类型：指令。 */
    public static final String TYPE_COMMAND = "command";
    /** 面板元素类型：链接跳转。 */
    public static final String TYPE_LINK = "link";

    /** 关联对象操作类型：添加。 */
    public static final String OP_ADD = "add";
    /** 关联对象操作类型：移除。 */
    public static final String OP_DEL = "del";

    private List<PanelItem> items;
    private String remark;
    private Integer version;

    /**
     * 面板元素。
     *
     * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
     * <tbody><tr><td>name</td> <td>string</td> <td>元素名称，最多 14 个字符</td></tr>
     * <tr><td>desc</td> <td>string</td> <td>元素描述，最多 30 个字符</td></tr>
     * <tr><td>type</td> <td>string</td> <td>元素类型：command/link</td></tr>
     * <tr><td>only_admin</td> <td>boolean</td> <td>是否仅管理员可操作</td></tr>
     * <tr><td>link</td> <td>string</td> <td>跳转链接 URL，仅 type=link 时有效</td></tr></tbody></table>
     */
    @Data
    @Accessors(chain = true)
    public static class PanelItem {
        private String name;
        private String desc;
        private String type;
        @JSONField(name = "only_admin")
        private Boolean onlyAdmin;
        private String link;
    }

    /**
     * 创建指令面板请求体。
     *
     * <table><thead><tr><th>名称</th> <th>类型</th> <th>必填</th> <th>描述</th></tr></thead>
     * <tbody><tr><td>scope</td> <td>string</td> <td>是</td> <td>生效场景：c2c/group/channel/dm</td></tr>
     * <tr><td>target_type</td> <td>string</td> <td>否</td> <td>作用范围：all/specific，channel 与 dm 仅支持 all</td></tr>
     * <tr><td>user_openids</td> <td>[]string</td> <td>否</td> <td>用户 openid 列表，仅 c2c 且 target_type=specific 有效，最多 20 个</td></tr>
     * <tr><td>group_openids</td> <td>[]string</td> <td>否</td> <td>群 openid 列表，仅 group 且 target_type=specific 有效，最多 20 个</td></tr>
     * <tr><td>panel</td> <td>Panel</td> <td>是</td> <td>面板配置内容</td></tr></tbody></table>
     */
    @Data
    @Accessors(chain = true)
    public static class CreateRequest {
        private String scope;
        @JSONField(name = "target_type")
        private String targetType;
        @JSONField(name = "user_openids")
        private List<String> userOpenids;
        @JSONField(name = "group_openids")
        private List<String> groupOpenids;
        private Panel panel;
    }

    /**
     * 修改指令面板请求体，将覆盖原有的面板元素列表和备注，不影响已关联的用户/群列表。
     */
    @Data
    @Accessors(chain = true)
    public static class UpdateRequest {
        private Panel panel;
    }

    /**
     * 修改指令面板关联对象请求体。
     *
     * <table><thead><tr><th>名称</th> <th>类型</th> <th>必填</th> <th>描述</th></tr></thead>
     * <tbody><tr><td>op</td> <td>string</td> <td>是</td> <td>操作类型：add/del</td></tr>
     * <tr><td>user_openids</td> <td>[]string</td> <td>否</td> <td>用户 openid 列表，仅 c2c 场景有效，最多 20 个</td></tr>
     * <tr><td>group_openids</td> <td>[]string</td> <td>否</td> <td>群 openid 列表，仅 group 场景有效，最多 20 个</td></tr></tbody></table>
     */
    @Data
    @Accessors(chain = true)
    public static class TargetRequest {
        private String op;
        @JSONField(name = "user_openids")
        private List<String> userOpenids;
        @JSONField(name = "group_openids")
        private List<String> groupOpenids;
    }

    /**
     * 创建指令面板响应，返回新的面板 ID。
     */
    @Data
    @Accessors(chain = true)
    public static class Created {
        @JSONField(name = "panel_id")
        private String panelId;
    }
}
