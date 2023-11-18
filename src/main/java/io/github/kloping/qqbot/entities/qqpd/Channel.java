package io.github.kloping.qqbot.entities.qqpd;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.AtAble;
import io.github.kloping.qqbot.api.DeleteAble;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.At;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.message.MessageReference;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
public class Channel implements SenderAndCidMidGetter, AtAble, DeleteAble {
    private Integer speakPermission;
    private Integer subType;
    private String ownerId;
    private String guildId;
    private String name;
    private String id;
    private Integer position;
    private String parentId;
    private Integer type;
    private Integer privateType;
    private String applicationId;
    public static final Map<String, String> SEND_MESSAGE_HEADERS = new HashMap<>();

    static {
        SEND_MESSAGE_HEADERS.put("Content-Type", "application/json");
        SEND_MESSAGE_HEADERS.put("Accept-Encoding", "*/*");
    }

    public static final Map<String, String> SEND_FORM_DATA_HEADERS = new HashMap<>();

    static {
        SEND_FORM_DATA_HEADERS.put("Content-Type", "multipart/form-data");
        SEND_FORM_DATA_HEADERS.put("Accept-Encoding", "*/*");
    }

    /**
     * 此方式发送的消息 为主动消息 会受到次数限制
     *
     * @param text
     * @param message
     * @return
     */
    @Override
    public Result<ActionResult> send(String text, RawMessage message) {
        RawPreMessage msg = new RawPreMessage(text);
        msg.setMessageReference(new MessageReference(message.getId()));
        return new Result<>(bot.messageBase.send(Channel.this.id, msg, SEND_MESSAGE_HEADERS));
    }

    /**
     * 此方式发送的消息 为主动消息 会受到次数限制
     *
     * @param text
     * @return
     */
    @Override
    public Result<ActionResult> send(String text) {
        return new Result<>(bot.messageBase.send(Channel.this.id, new RawPreMessage(text), SEND_MESSAGE_HEADERS));
    }

    /**
     * 此方式发送的消息 为主动消息 会受到次数限制
     *
     * @param packet
     * @return
     */
    @Override
    public Result<ActionResult> send(MessagePacket packet) {
        RawPreMessage msg = new RawPreMessage();
        BaseUtils.packet2pre(packet, msg);
        return new Result<>(bot.messageBase.send(Channel.this.id, msg, SEND_MESSAGE_HEADERS));
    }

    /**
     * 此方式发送的消息 为主动消息 会受到次数限制
     *
     * @param msg
     * @return
     */
    @Override
    public Result<ActionResult> send(RawPreMessage msg) {
        return new Result<>(bot.messageBase.send(Channel.this.id, msg, SEND_MESSAGE_HEADERS));
    }

    @Override
    public At at() {
        return new At("channel", getId());
    }

    @Override
    public Result<ActionResult> send(SendAble msg) {
        return (msg.send(this));
    }

    @Override
    public Object delete() {
        getBot().channelBase.delete(Channel.this.getId());
        return null;
    }

    @Override
    public String getCid() {
        return getId();
    }

    @Getter
    @JSONField(serialize = false, deserialize = false)
    private Bot bot;

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public EnvType getEnvType() {
        return EnvType.GUILD;
    }
}