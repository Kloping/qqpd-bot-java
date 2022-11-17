package io.github.kloping.qqbot.api;

import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.interfaces.Sender;
import io.github.kloping.qqbot.api.message.PreMessage;
import io.github.kloping.qqbot.api.message.audited.MessageAudited;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;


/**
 * <h3 id="channel"><a href="#channel" class="header-anchor">#</a> Channel</h3>
 * <table><thead><tr><th>字段名</th><th>类型</th><th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>子频道 id</td></tr> <tr><td>guild_id</td> <td>string</td> <td>频道 id</td></tr> <tr><td>name</td> <td>string</td> <td>子频道名</td></tr> <tr><td>type</td> <td>int</td> <td>子频道类型 <a href="#channeltype">ChannelType</a></td></tr> <tr><td>sub_type</td> <td>int</td> <td>子频道子类型 <a href="#channelsubtype">ChannelSubType</a></td></tr> <tr><td>position</td> <td>int</td> <td>排序值，具体请参考 <a href="#%E6%9C%89%E5%85%B3-position-%E7%9A%84%E8%AF%B4%E6%98%8E">有关 position 的说明</a></td></tr> <tr><td>parent_id</td> <td>string</td> <td>所属分组 id，仅对子频道有效，对 <code>子频道分组（ChannelType=4）</code> 无效</td></tr> <tr><td>owner_id</td> <td>string</td> <td>创建人 id</td></tr> <tr><td>private_type</td> <td>int</td> <td>子频道私密类型 <a href="#privatetype">PrivateType</a></td></tr> <tr><td>speak_permission</td> <td>int</td> <td>子频道发言权限 <a href="#speakpermission">SpeakPermission</a></td></tr> <tr><td>application_id</td> <td>string</td> <td>用于标识应用子频道应用类型，仅应用子频道时会使用该字段，具体定义请参考 <a href="#%E5%BA%94%E7%94%A8%E5%AD%90%E9%A2%91%E9%81%93%E7%9A%84%E5%BA%94%E7%94%A8%E7%B1%BB%E5%9E%8B">应用子频道的应用类型</a></td></tr> <tr><td>permissions</td> <td>string</td> <td>用户拥有的子频道权限 <a href="/wiki/develop/api/openapi/channel_permissions/model.html#permissions" class="">Permissions</a></td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Channel implements Sender {
    private Number speak_permission;
    private Number sub_type;
    private String owner_id;
    private String guild_id;
    private String name;
    private String id;
    private Number position;
    private Number type;
    private Number private_type;
    private String application_id;
    public static final Map<String, String> MAP = new HashMap<>();

    static {
        MAP.put("Content-Type", "application/json");
    }

    @Override
    public MessageAudited send(String text) {
        return Resource.messageBase.send(id, new PreMessage(text), MAP);
    }

    @Override
    public MessageAudited send(PreMessage msg) {
        return Resource.messageBase.send(id, msg, MAP);
    }
}