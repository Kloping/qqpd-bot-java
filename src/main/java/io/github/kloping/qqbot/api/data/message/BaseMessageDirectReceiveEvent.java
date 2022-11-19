package io.github.kloping.qqbot.api.data.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.data.MessagePacket;
import io.github.kloping.qqbot.api.interfaces.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.api.qqpd.Guild;
import io.github.kloping.qqbot.api.qqpd.message.DirectMessage;
import io.github.kloping.qqbot.api.qqpd.message.Message;
import io.github.kloping.qqbot.api.qqpd.message.PreMessage;
import io.github.kloping.qqbot.api.qqpd.message.audited.MessageAudited;

/**
 * @author github.kloping
 */
public class BaseMessageDirectReceiveEvent extends BaseMessageReceiveEvent implements MessageDirectReceiveEvent {
    public BaseMessageDirectReceiveEvent(Message message, JSONObject jo) {
        super();
        this.message = message;
        this.directMessage = DirectMessage.messageAsDirectMessage(message);
        this.metadata = jo;
        this.guild = Resource.starter.getBot().getGuild(getMessage().getGuildId());
        this.sender = getMessage().getMember();
        this.srcGuildId = getMessage().getSrcGuildId();
        this.content = getMessage().getContent() == null ? "" : getMessage().getContent();
        this.srcGuild = Resource.starter.getBot().getGuild(getSrcGuildId());
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
    public MessageAudited send(String text) {
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
    public MessageAudited send(String text, Message message) {
        return sendDirect(text, message);
    }

    /**
     * 替换默认
     *
     * @param packet
     * @return
     */
    @Override
    public MessageAudited send(MessagePacket packet) {
        return sendDirect(packet);
    }

    /**
     * 替换默认
     *
     * @param msg
     * @return
     */
    @Override
    public MessageAudited send(PreMessage msg) {
        return sendDirect(msg);
    }

    @Override
    public MessageAudited sendDirect(String text) {
        return getDirectMessage().sendDirect(text);
    }

    @Override
    public MessageAudited sendDirect(String text, Message message) {
        return getDirectMessage().sendDirect(text, message);
    }

    @Override
    public MessageAudited sendDirect(MessagePacket packet) {
        return getDirectMessage().sendDirect(packet);
    }

    @Override
    public MessageAudited sendDirect(PreMessage msg) {
        return getDirectMessage().sendDirect(msg);
    }
}