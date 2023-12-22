package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.event.ChannelEvent;
import io.github.kloping.qqbot.api.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * @author github.kloping
 */
public class BaseMessageChannelReceiveEvent extends BaseMessageEvent implements ChannelEvent, MessageChannelReceiveEvent {
    public BaseMessageChannelReceiveEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
        this.channel = getGuild().getChannel(message.getChannelId());
        this.sender = getGuild().getMember(message.getAuthor().getId());
        this.channelId = getChannel().getId();
    }

    protected String content;
    protected String channelId;

    @Override
    public String getContent() {
        return content == null ? content = getMessage().toString() : content;
    }

    @Override
    public String getChannelId() {
        return channelId;
    }

    @Override
    public String toString() {
        return String.format("[type(%s) %s].%s member(%s)=>%s"
                , EnvType.GUILD.name()
                , getGuild().getName()
                , getChannel().getName()
                , getSender().getNick()
                , getRawMessage().toString0()
        );
    }

    @Override
    public void addEmoji(Emoji emoji) {
       getRawMessage().addEmoji(emoji);
    }

    @Override
    public void removeEmoji(Emoji emoji) {
        getRawMessage().addEmoji(emoji);
    }

    @Override
    public String getClassName() {
        return MessageChannelReceiveEvent.class.getSimpleName();
    }
}