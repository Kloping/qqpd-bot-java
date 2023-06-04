package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.MessagePre;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.message.Message;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.entities.qqpd.message.audited.MessageAudited;
import io.github.kloping.qqbot.impl.MessagePacket;

/**
 * @author github.kloping
 */
public class BaseMessageEvent implements MessageEvent {
    protected Message message;
    protected JSONObject metadata;
    protected Guild guild;
    protected Member sender;
    protected Channel channel;

    protected Bot bot;

    public BaseMessageEvent() {
    }

    public BaseMessageEvent(Message message, JSONObject jo, Bot bot) {
        this.message = message;
        this.metadata = jo;
        this.bot = bot;
        this.guild = getBot().getGuild(message.getGuildId());
        this.channel = getGuild().channelMap().get(message.getChannelId());
        this.sender = getGuild().getMember(message.getAuthor().getId());
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
    public MessageAudited send(RawPreMessage msg) {
        return getMessage().send(msg);
    }

    @Override
    public Bot getBot() {
        return bot;
    }

    @Override
    public MessageAudited send(MessagePre msg) {
        return getMessage().send(msg);
    }
}
