package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.message.DirectMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;

/**
 * @author github.kloping
 */
public class BaseMessageDirectReceiveEvent extends BaseMessageReceiveEvent implements MessageDirectReceiveEvent {
    public BaseMessageDirectReceiveEvent(RawMessage message, JSONObject jo, Bot bot) {
        super();
        this.bot = bot;
        this.directMessage = DirectMessage.messageAsDirectMessage(message);
        this.message = this.directMessage;
        this.metadata = jo;
        this.srcGuildId = getRawMessage().getSrcGuildId();
        this.content = getRawMessage().getContent() == null ? "" : getRawMessage().getContent();
        this.srcGuild = getBot().getGuild(getSrcGuildId());
        this.guild = getBot().getGuild(getSrcGuildId());
        this.sender = getRawMessage().getMember();
    }

    @Override
    public Member getSender() {
        return super.getSender();
    }

    private String srcGuildId;
    private Guild srcGuild;
    private DirectMessage directMessage;

    @Override
    public DirectMessage getDirectMessage() {
        return directMessage;
    }

    @Override
    public String getSrcGuildId() {
        return srcGuildId;
    }

    @Override
    public Guild getSrcGuild() {
        return srcGuild;
    }

    /**
     * 替换默认
     *
     * @param text
     * @return
     */
    @Override
    public Result<ActionResult> send(String text) {
        return sendDirect(text);
    }

    /**
     * 替换默认
     *
     * @param text
     * @param message
     * @return
     */
    @Override
    public Result<ActionResult> send(String text, RawMessage message) {
        return sendDirect(text, message);
    }

    /**
     * 替换默认
     *
     * @param packet
     * @return
     */
    @Override
    public Result<ActionResult> send(MessagePacket packet) {
        return sendDirect(packet);
    }

    /**
     * 替换默认
     *
     * @param msg
     * @return
     */
    @Override
    public Result<ActionResult> send(RawPreMessage msg) {
        return sendDirect(msg);
    }

    @Override
    public Result<ActionResult> sendDirect(String text) {
        return getDirectMessage().sendDirect(text);
    }

    @Override
    public Result<ActionResult> sendDirect(String text, RawMessage message) {
        return getDirectMessage().sendDirect(text, message);
    }

    @Override
    public Result<ActionResult> sendDirect(MessagePacket packet) {
        return getDirectMessage().sendDirect(packet);
    }

    @Override
    public Result<ActionResult> sendDirect(RawPreMessage msg) {
        return getDirectMessage().sendDirect(msg);
    }

    @Override
    public String toString() {
        return String.format("[channel(%s)]direct(%s)=>%s"
                , getDirectMessage().getChannelId()
                , getSender().getNick()
                , getContent());
    }
}