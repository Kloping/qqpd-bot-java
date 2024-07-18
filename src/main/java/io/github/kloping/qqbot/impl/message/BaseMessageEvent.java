package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.event.ChannelEvent;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.Member;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.ActionResult;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.impl.MessagePacket;
import io.github.kloping.qqbot.utils.BaseUtils;

/**
 * @author github.kloping
 */
public abstract class BaseMessageEvent implements ChannelEvent, MessageEvent<Member, Channel> {
    protected RawMessage message;
    protected JSONObject metadata;
    protected Member sender;
    protected Guild guild;
    protected Channel channel;

    protected Bot bot;

    public BaseMessageEvent() {
    }

    public BaseMessageEvent(RawMessage message, JSONObject jo, Bot bot) {
        this.message = message;
        this.metadata = jo;
        this.bot = bot;
        this.guild = getBot().getGuild(message.getGuildId());
        this.channel = getGuild().getChannel(message.getChannelId());
        this.sender = getGuild().getMember(message.getAuthor().getId());
    }

    @Override
    public String toString() {
        return String.format("[type(%s) %s].%s:%s"
                , EnvType.GUILD.name()
                , getSubject().getId()
                , getSender().getId()
                , getRawMessage().toString0()
        );
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public Channel getSubject() {
        return getChannel();
    }

    @Override
    public Guild getGuild() {
        return guild;
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
    public Member getSender() {
        return sender;
    }

    @Override
    public Result<ActionResult> send(String text) {
        return getRawMessage().send(text);
    }

    @Override
    public Result<ActionResult> send(String text, RawMessage message) {
        return getRawMessage().send(text, message);
    }

    @Override
    public Result<ActionResult> send(MessagePacket packet) {
        return getRawMessage().send(packet);
    }

    @Override
    public Result<ActionResult> send(RawPreMessage msg) {
        return getRawMessage().send(msg);
    }

    @Override
    public Bot getBot() {
        return bot;
    }

    @Override
    public Result<ActionResult> send(SendAble msg) {
        return getRawMessage().send(msg);
    }

    protected MessageChain chain;

    @Override
    public MessageChain getMessage() {
        return chain == null ? chain = BaseUtils.parseToMessageChain(getRawMessage(), filters) : chain;
    }

    @JSONField(serialize = false, deserialize = false)
    private Class<?>[] filters = null;

    @Override
    public void setFilter(Class<?>[] filters) {
        this.filters = filters;
    }

    @Override
    public String getId() {
        return getRawMessage().getId();
    }
}
