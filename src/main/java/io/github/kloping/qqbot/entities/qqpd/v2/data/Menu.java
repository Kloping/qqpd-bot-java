package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 全局自定义菜单配置（仅 C2C 单聊场景，设置后对所有用户生效）。
 *
 * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>items</td> <td>[]MenuItem</td> <td>菜单项列表，最多 10 个，按列表顺序从左到右展示</td></tr></tbody></table>
 *
 * <p>官方文档：<a href="https://bot.q.qq.com/wiki/develop/api-v2/server-inter/menu-panel/">自定义菜单与指令面板</a></p>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class Menu {
    /** 按钮类型：开关。 */
    public static final String TYPE_SWITCH = "switch";
    /** 按钮类型：发送消息。 */
    public static final String TYPE_SEND_MESSAGE = "send_message";
    /** 按钮类型：链接跳转。 */
    public static final String TYPE_LINK = "link";
    /** 按钮类型：含子菜单的折叠项。 */
    public static final String TYPE_MENU = "menu";

    /** 菜单项列表，最多 10 个，按列表顺序从左到右展示。 */
    private List<MenuItem> items;

    /**
     * 菜单项。
     *
     * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
     * <tbody><tr><td>name</td> <td>string</td> <td>按钮名称，最多 10 个字符，一个中文汉字算 2 个字符</td></tr>
     * <tr><td>type</td> <td>string</td> <td>按钮类型：switch/send_message/link/menu</td></tr>
     * <tr><td>sub_menu_items</td> <td>[]SubMenuItem</td> <td>子菜单列表，仅 type=menu 时有效</td></tr>
     * <tr><td>send_message</td> <td>string</td> <td>发送的内容，仅 type=send_message 时有效</td></tr>
     * <tr><td>link</td> <td>string</td> <td>跳转链接 URL，仅 type=link 时有效，须以 https:// 开头</td></tr>
     * <tr><td>switch</td> <td>SwitchConfig</td> <td>开关配置，仅 type=switch 时有效</td></tr></tbody></table>
     */
    @Data
    @Accessors(chain = true)
    public static class MenuItem {
        private String name;
        private String type;
        @JSONField(name = "sub_menu_items")
        private List<SubMenuItem> subMenuItems;
        @JSONField(name = "send_message")
        private String sendMessage;
        private String link;
        /** 官方字段名为 switch，作为 Java 保留字使用 switchConfig 承载。 */
        @JSONField(name = "switch")
        private SwitchConfig switchConfig;
    }

    /**
     * 子菜单项，仅 type=menu 的菜单项有效，最多 5 个，不支持再嵌套。
     */
    @Data
    @Accessors(chain = true)
    public static class SubMenuItem {
        private String name;
        private String type;
        @JSONField(name = "send_message")
        private String sendMessage;
        private String link;
    }

    /**
     * 开关配置。
     *
     * <table><thead><tr><th>名称</th> <th>类型</th> <th>描述</th></tr></thead>
     * <tbody><tr><td>switch_id</td> <td>string</td> <td>开关唯一标识，用户切换后消息 ext 字段会携带该标识</td></tr>
     * <tr><td>default</td> <td>boolean</td> <td>开关初始状态，true 默认打开</td></tr></tbody></table>
     */
    @Data
    @Accessors(chain = true)
    public static class SwitchConfig {
        @JSONField(name = "switch_id")
        private String switchId;
        /** 官方字段名为 default，作为 Java 保留字使用 defaultOn 承载。 */
        @JSONField(name = "default")
        private Boolean defaultOn;
    }
}
