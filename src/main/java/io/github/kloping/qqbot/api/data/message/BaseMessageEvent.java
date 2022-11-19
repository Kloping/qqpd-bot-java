package io.github.kloping.qqbot.api.data.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.data.MessagePacket;
import io.github.kloping.qqbot.api.interfaces.message.MessageEvent;
import io.github.kloping.qqbot.api.qqpd.Channel;
import io.github.kloping.qqbot.api.qqpd.Guild;
import io.github.kloping.qqbot.api.qqpd.Member;
import io.github.kloping.qqbot.api.qqpd.interfaces.DirectSender;
import io.github.kloping.qqbot.api.qqpd.interfaces.Sender;
import io.github.kloping.qqbot.api.qqpd.message.Message;
import io.github.kloping.qqbot.api.qqpd.message.PreMessage;
import io.github.kloping.qqbot.api.qqpd.message.audited.MessageAudited;

/**
 * @author github.kloping
 */
public class BaseMessageEvent implements MessageEvent {
    protected Message message;
    protected JSONObject metadata;
    protected Guild guild;
    protected Member sender;
    protected Channel channel;

    public BaseMessageEvent() {
    }

    public BaseMessageEvent(Message message, JSONObject jo) {
        this.message = message;
        this.metadata = jo;
        this.guild = Resource.starter.getBot().getGuild(message.getGuildId());
        this.channel = getGuild().channelMap().get(message.getChannelId());
        this.sender = message.getMember();
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public JSONObject getMetadata() {
        return metadata;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public Member getSender() {
        return sender;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public MessageAudited sendDirect(String text) {
        return getMessage().sendDirect(text);
    }

    @Override
    public MessageAudited sendDirect(String text, Message message) {
        return getMessage().sendDirect(text, message);
    }

    @Override
    public MessageAudited sendDirect(MessagePacket packet) {
        return getMessage().sendDirect(packet);
    }

    @Override
    public MessageAudited sendDirect(PreMessage msg) {
        return getMessage().sendDirect(msg);
    }

    @Override
    public MessageAudited send(String text) {
        return getMessage().send(text);
    }

    @Override
    public MessageAudited send(String text, Message message) {
        return getMessage().send(text, message);
    }

    @Override
    public MessageAudited send(MessagePacket packet) {
        return getMessage().send(packet);
    }

    @Override
    public MessageAudited send(PreMessage msg) {
        return getMessage().send(msg);
    }
}
