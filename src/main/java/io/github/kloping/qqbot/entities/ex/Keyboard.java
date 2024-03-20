package io.github.kloping.qqbot.entities.ex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 * 按钮可单独发送 <br>
 * 但与md一并发送时必须使用模板
 * <br>
 * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>否</td> <td>按钮ID：在一个keyboard消息内设置唯一</td></tr> <tr><td>render_data.label</td> <td>string</td> <td>是</td> <td>按钮上的文字</td></tr> <tr><td>render_data.visited_label</td> <td>string</td> <td>是</td> <td>点击后按钮的上文字</td></tr> <tr><td>render_data.style</td> <td>int</td> <td>是</td> <td>按钮样式：0 灰色线框，1 蓝色线框</td></tr> <tr><td>action.type</td> <td>int</td> <td>是</td> <td>设置 0 跳转按钮：http 或 小程序 客户端识别 scheme，设置 1 回调按钮：回调后台接口, data 传给后台，设置 2 指令按钮：自动在输入框插入 @bot data</td></tr> <tr><td>action.permisson.type</td> <td>int</td> <td>是</td> <td>0 指定用户可操作，1 仅管理者可操作，2 所有人可操作，3 指定身份组可操作（仅频道可用）</td></tr> <tr><td>action.permisson.specify_user_ids</td> <td>array</td> <td>否</td> <td>有权限的用户 id 的列表</td></tr> <tr><td>action.permisson.specify_role_ids</td> <td>array</td> <td>否</td> <td>有权限的身份组 id 的列表（仅频道可用）</td></tr> <tr><td>action.data</td> <td>string</td> <td>是</td> <td>操作相关的数据</td></tr> <tr><td>action.reply</td> <td>bool</td> <td>否</td> <td>指令按钮可用，指令是否带引用回复本消息，默认 false。支持版本 8983</td></tr> <tr><td>action.enter</td> <td>bool</td> <td>否</td> <td>指令按钮可用，点击按钮后直接自动发送 data，默认 false。支持版本 8983</td></tr> <tr><td>action.anchor</td> <td>int</td> <td>否</td> <td>本字段仅在指令按钮下有效，设置后后会忽略 action.enter 配置。<br>设置为 1 时 ，点击按钮自动唤起启手Q选图器，其他值暂无效果。<br>（仅支持手机端版本 8983+ 的单聊场景，桌面端不支持）</td></tr> <tr><td>action.click_limit</td> <td>int</td> <td>否</td> <td>【已弃用】可操作点击的次数，默认不限</td></tr> <tr><td>action.at_bot_show_channel_list</td> <td>bool</td> <td>否</td> <td>【已弃用】指令按钮可用，弹出子频道选择器，默认 false</td></tr> <tr><td>action.unsupport_tips</td> <td>string</td> <td>是</td> <td>客户端不支持本action的时候，弹出的toast文案</td></tr></tbody></table>
 *
 *
 * @author github.kloping
 */
public class Keyboard implements SendAble {
    @Getter
    private String id = null;

    /**
     * 模板创建方式必须与markdown搭配
     * {@link Markdown#setKeyboard}
     *
     * @param id
     */
    public Keyboard(String id) {
        this.id = id;
    }

    private Keyboard() {
    }

    @Getter
    private JSONObject content;

    @Override
    public Result send(SenderAndCidMidGetter er) {
        if (id != null) {
            er.getBot().logger.error("keyboard id is not null.");
            return null;
        }
        if (er.getEnvType() == EnvType.GROUP) {
            V2MsgData v2MsgData = new V2MsgData();
            v2MsgData.setMsg_type(2);
            v2MsgData.setKeyboard(this);
            v2MsgData.setMsg_id(er.getMid());
            String d0 = JSON.toJSONString(v2MsgData);
            return new Result(er.getBot().groupBaseV2.send(er.getCid(), d0, SEND_MESSAGE_HEADERS));
        } else if (er.getEnvType() == EnvType.GUILD) {
            RawPreMessage preMessage = new RawPreMessage();
            preMessage.setMsgId(er.getMid());
            return new Result(er.getBot().messageBase.send(er.getCid(), preMessage, SEND_MESSAGE_HEADERS));
        }
        return er.send(this);
    }

    public static class KeyboardBuilder {
        public static KeyboardBuilder create() {
            return new KeyboardBuilder();
        }

        private Integer id = 1;

        private Integer requestIdIndex() {
            return id++;
        }

        public RowBuilder addRow() {
            return new RowBuilder(this);
        }

        private List list = new LinkedList();

        public Keyboard build() {
            Keyboard keyboard = new Keyboard();
            JSONObject rows = new JSONObject();
            rows.put("rows", JSONArray.parseArray(JSON.toJSONString(list)));
            keyboard.content = rows;
            return keyboard;
        }
    }

    public static class RowBuilder {
        private KeyboardBuilder builder;

        public RowBuilder(KeyboardBuilder builder) {
            this.builder = builder;
        }

        private Buttons buttons = new Buttons();

        public ButtonBuilder addButton() {
            return new ButtonBuilder(this);
        }

        public RowBuilder addButton(Button button) {
            buttons.buttons.add(button);
            return this;
        }

        public KeyboardBuilder build() {
            builder.list.add(buttons);
            return builder;
        }
    }

    public static class ButtonBuilder {
        private RowBuilder builder;
        private Button button;

        public ButtonBuilder(RowBuilder builder) {
            this.builder = builder;
            button = new Button();
            button.id = builder.builder.requestIdIndex().toString();
        }

        /**
         * label – 按钮上的文字
         *
         * @param text
         * @return
         */
        public ButtonBuilder setLabel(String text) {
            button.render_data.label = text;
            return this;
        }

        /**
         * visited_label – 按钮上的文字
         *
         * @param text
         * @return
         */
        public ButtonBuilder setVisitedLabel(String text) {
            button.render_data.visited_label = text;
            return this;
        }

        /**
         * render_data.style	int	是	按钮样式：0 灰色线框，1 蓝色线框
         *
         * @param style
         * @return
         */
        public ButtonBuilder setStyle(Integer style) {
            button.render_data.style = style;
            return this;
        }

        /**
         * @param data 操作相关的数据
         * @return
         */
        public ButtonBuilder setActionData(String data) {
            button.action.data = data;
            return this;
        }

        /**
         * @param type 设置 0 跳转按钮：http 或 小程序 客户端识别 scheme，设置 1 回调按钮：回调后台接口, data 传给后台，设置 2 指令按钮：自动在输入框插入 @bot data
         * @return
         */
        public ButtonBuilder setActionType(Integer type) {
            button.action.type = type;
            return this;
        }

        /**
         * @param enter 指令按钮可用，点击按钮后直接自动发送 data，默认 false。支持版本 8983
         * @return
         */
        public ButtonBuilder setActionEnter(boolean enter) {
            button.action.enter = enter;
            return this;
        }

        /**
         * @param reply 指令按钮可用，指令是否带引用回复本消息，默认 false。支持版本 8983
         * @return
         */
        public ButtonBuilder setActionReply(boolean reply) {
            button.action.reply = reply;
            return this;
        }

        /**
         * @param tips 客户端不支持本action的时候，弹出的toast文案
         * @return
         */
        public ButtonBuilder setUnSupportTips(String tips) {
            button.action.unsupport_tips = tips;
            return this;
        }

        /**
         * @param type 0 指定用户可操作，1 仅管理者可操作，2 所有人可操作，3 指定身份组可操作（仅频道可用）
         * @return
         */
        public ButtonBuilder setPermissionType(Integer type) {
            button.action.permission.type = type;
            return this;
        }

        public ButtonBuilder setPermission(Permission permission) {
            button.action.permission = permission;
            return this;
        }

        public RowBuilder build() {
            builder.buttons.buttons.add(button);
            return builder;
        }
    }

    @Getter
    public static class Buttons {
        private List<Button> buttons = new LinkedList<>();
    }

    @Getter
    public static class Button {
        private RenderData render_data = new RenderData();
        private Action action = new Action();
        private String id;
    }

    @Getter
    public static class RenderData {
        private String label;
        private String visited_label;
        private Integer style = 1;
    }

    @Getter
    public static class Action {
        private String data = "按钮";
        private Integer type = 2;
        private Permission permission = new Permission();
        private String unsupport_tips = "客户端版本过低";
        private Boolean reply = false;
        private Boolean enter = false;
    }

    /**
     * action.permisson.type	int	是	0 指定用户可操作，1 仅管理者可操作，2 所有人可操作，3 指定身份组可操作（仅频道可用） <br>
     * action.permisson.specify_user_ids	array	否	有权限的用户 id 的列表 <br>
     * action.permisson.specify_role_ids	array	否	有权限的身份组 id 的列表（仅频道可用）
     */
    @Getter
    public static class Permission {
        private String[] specify_role_ids = new String[]{};
        private String[] specify_user_ids = new String[]{};
        private Integer type = 2;

        public Permission() {
        }

        public Permission(String[] specify_role_ids, String[] specify_user_ids, Integer type) {
            this.specify_role_ids = specify_role_ids;
            this.specify_user_ids = specify_user_ids;
            this.type = type;
        }
    }
}
