package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.impl.MessagePacket;
import io.github.kloping.qqbot.utils.BaseUtils;

/**
 * @author github.kloping
 */
public abstract class BaseMessageEvent implements MessageEvent {
    protected RawMessage message;
    protected JSONObject metadata;
    protected Guild guild;
    protected Member sender;
    protected Channel channel;

    protected Bot bot;

    public BaseMessageEvent() {
    }

    public BaseMessageEvent(RawMessage message, JSONObject jo, Bot bot) {
        this.message = message;
        this.metadata = jo;
        this.bot = bot;
        this.guild = getBot().getGuild(message.getGuildId());
        this.channel = getGuild().channelMap().get(message.getChannelId());
        this.sender = getGuild().getMember(message.getAuthor().getId());
    }

    @Override
    public RawMessage getRawMessage() {
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
    public ActionResult send(String text) {
        return getRawMessage().send(text);
    }

    @Override
    public ActionResult send(String text, RawMessage message) {
        return getRawMessage().send(text, message);
    }

    @Override
    public ActionResult send(MessagePacket packet) {
        return getRawMessage().send(packet);
    }

    @Override
    public ActionResult send(RawPreMessage msg) {
        return getRawMessage().send(msg);
    }

    @Override
    public Bot getBot() {
        return bot;
    }

    @Override
    public ActionResult send(SendAble msg) {
        return getRawMessage().send(msg);
    }

    @Override
    public MessageChain getMessage() {
        return BaseUtils.parseToMessageChain(getRawMessage());
    }
}
