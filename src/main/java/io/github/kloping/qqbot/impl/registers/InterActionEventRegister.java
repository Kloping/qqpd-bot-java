package io.github.kloping.qqbot.impl.registers;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.BaseInterActionEvent;
import io.github.kloping.qqbot.network.Events;

/**
 * @author github.kloping
 */
@Entity
public class InterActionEventRegister implements Events.EventRegister {

    /**
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>平台方事件 ID，可以用于被动消息发送</td></tr> <tr><td>type</td> <td>int</td> <td>消息按钮： 11，单聊快捷菜单：12</td></tr> <tr><td>scene</td> <td>string</td> <td>事件发生的场景：c2c、group、guild</td></tr> <tr><td>chat_type</td> <td>int</td> <td>0 频道场景，1 群聊场景，2 单聊场景</td></tr> <tr><td>timestamp</td> <td>string</td> <td>触发时间 RFC 3339 格式</td></tr> <tr><td>guild_id</td> <td>string</td> <td>频道的 openid ，仅在频道场景提供该字段</td></tr> <tr><td>channel_id</td> <td>string</td> <td>文字子频道的 openid，仅在频道场景提供该字段</td></tr> <tr><td>user_openid</td> <td>string</td> <td>单聊单聊按钮触发x，的用户 openid，仅在单聊场景提供该字段</td></tr> <tr><td>group_openid</td> <td>string</td> <td>群的 openid，仅在群聊场景提供该字段</td></tr> <tr><td>group_member_openid</td> <td>string</td> <td>按钮触发用户，群聊的群成员 openid，仅在群聊场景提供该字段</td></tr> <tr><td>data.resoloved.button_data</td> <td>string</td> <td>操作按钮的 data 字段值（在发送消息按钮时设置）</td></tr> <tr><td>data.resoloved.button_id</td> <td>string</td> <td>操作按钮的 id 字段值（在发送消息按钮时设置）</td></tr> <tr><td>data.resoloved.user_id</td> <td>string</td> <td>操作的用户 userid，仅频道场景提供该字段</td></tr> <tr><td>data.resoloved.feature_id</td> <td>string</td> <td>操作按钮的 id 字段值，仅自定义菜单提供该字段（在管理端设置）</td></tr> <tr><td>data.resoloved.message_id</td> <td>string</td> <td>操作的消息id，目前仅频道场景提供该字段</td></tr> <tr><td>version</td> <td>int</td> <td>默认 1</td></tr> <tr><td>application_id</td> <td>string</td> <td>机器人的 appid</td></tr></tbody></table>
     */
    public static final String INTERACTION_CREATE = "INTERACTION_CREATE";

    @AutoStandAfter
    private void rN(Events events) {
        events.register(INTERACTION_CREATE, this);
    }

    @AutoStand
    Bot bot;

    @AutoStand
    Logger logger;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        Event event = null;
        if (t.equals(INTERACTION_CREATE)) {
            event = new BaseInterActionEvent(bot, mateData);
        }
        return event;
    }
}
