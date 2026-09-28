import com.alibaba.fastjson.JSON;
import io.github.kloping.qqbot.entities.qqpd.v2.data.Menu;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MenuData;
import io.github.kloping.qqbot.entities.qqpd.v2.data.Panel;
import io.github.kloping.qqbot.entities.qqpd.v2.data.PanelDetail;
import io.github.kloping.qqbot.entities.qqpd.v2.data.PanelRecordPage;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/** 自定义菜单与指令面板模型映射回归测试。 */
public class MenuPanelModelTest {

    @Test
    public void parsesGlobalMenu() {
        String json = "{\"menu\":{\"items\":["
                + "{\"type\":\"send_message\",\"name\":\"帮助\",\"send_message\":\"/help\"},"
                + "{\"type\":\"menu\",\"name\":\"更多\",\"sub_menu_items\":["
                + "{\"type\":\"link\",\"name\":\"官网\",\"link\":\"https://example.com\"}]},"
                + "{\"type\":\"switch\",\"name\":\"搜索\",\"switch\":{\"switch_id\":\"search\",\"default\":true}}"
                + "]},\"version\":1}";
        MenuData data = JSON.parseObject(json, MenuData.class);
        assertEquals(Integer.valueOf(1), data.getVersion());
        assertEquals(3, data.getMenu().getItems().size());
        Menu.MenuItem first = data.getMenu().getItems().get(0);
        assertEquals(Menu.TYPE_SEND_MESSAGE, first.getType());
        assertEquals("/help", first.getSendMessage());
        Menu.MenuItem second = data.getMenu().getItems().get(1);
        assertEquals("https://example.com", second.getSubMenuItems().get(0).getLink());
        Menu.MenuItem third = data.getMenu().getItems().get(2);
        assertEquals("search", third.getSwitchConfig().getSwitchId());
        assertTrue(third.getSwitchConfig().getDefaultOn());
    }

    @Test
    public void serializesMenuSwitchAsReservedFieldNames() {
        Menu.MenuItem item = new Menu.MenuItem().setType(Menu.TYPE_SWITCH).setName("搜索")
                .setSwitchConfig(new Menu.SwitchConfig().setSwitchId("search").setDefaultOn(true));
        String json = JSON.toJSONString(item);
        assertTrue(json, json.contains("\"switch\":"));
        assertTrue(json, json.contains("\"switch_id\":\"search\""));
        assertTrue(json, json.contains("\"default\":true"));
        assertFalse(json, json.contains("switchConfig"));
    }

    @Test
    public void parsesPanelRecordPage() {
        String json = "{\"records\":[{\"panel_id\":\"p_1\",\"scope\":\"c2c\",\"target_type\":\"all\","
                + "\"panel\":{\"items\":[{\"type\":\"command\",\"name\":\"查询天气\",\"desc\":\"查询当前天气\"}]},"
                + "\"created_at\":\"2024-01-15T10:30:00Z\",\"updated_at\":\"2024-01-15T10:30:00Z\",\"version\":1}],"
                + "\"next_cursor\":\"\",\"is_end\":true}";
        PanelRecordPage page = JSON.parseObject(json, PanelRecordPage.class);
        assertEquals(1, page.getRecords().size());
        assertEquals("p_1", page.getRecords().get(0).getPanelId());
        assertEquals(Panel.TARGET_ALL, page.getRecords().get(0).getTargetType());
        assertEquals("查询天气", page.getRecords().get(0).getPanel().getItems().get(0).getName());
        assertEquals("", page.getNextCursor());
        assertTrue(page.getIsEnd());
    }

    @Test
    public void parsesPanelDetailWithTargets() {
        String json = "{\"panel_id\":\"p_x8k2\",\"scope\":\"group\",\"target_type\":\"specific\","
                + "\"panel\":{\"items\":[{\"type\":\"command\",\"name\":\"群签到\",\"only_admin\":true}]},"
                + "\"version\":1,\"user_openids\":[],\"group_openids\":[\"g1\"]}";
        PanelDetail detail = JSON.parseObject(json, PanelDetail.class);
        assertEquals("p_x8k2", detail.getPanelId());
        assertEquals(Arrays.asList("g1"), detail.getGroupOpenids());
        assertTrue(detail.getUserOpenids().isEmpty());
        assertTrue(detail.getPanel().getItems().get(0).getOnlyAdmin());
    }

    @Test
    public void serializesPanelCreateRequest() {
        Panel.CreateRequest request = new Panel.CreateRequest()
                .setScope(Panel.SCOPE_GROUP)
                .setTargetType(Panel.TARGET_SPECIFIC)
                .setGroupOpenids(Arrays.asList("g1", "g2"))
                .setPanel(new Panel().setRemark("备注").setItems(Arrays.asList(
                        new Panel.PanelItem().setType(Panel.TYPE_LINK).setName("官网").setLink("https://example.com"))));
        String json = JSON.toJSONString(request);
        assertTrue(json, json.contains("\"target_type\":\"specific\""));
        assertTrue(json, json.contains("\"group_openids\":[\"g1\",\"g2\"]"));
        assertTrue(json, json.contains("\"remark\":\"备注\""));
    }
}
