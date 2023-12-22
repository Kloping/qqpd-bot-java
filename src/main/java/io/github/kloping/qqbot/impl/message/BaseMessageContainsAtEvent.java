package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageContainsAtEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * @author github.kloping
 */
public class BaseMessageContainsAtEvent extends BaseMessageChannelReceiveEvent implements MessageContainsAtEvent {
    public BaseMessageContainsAtEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo,bot);
        this.channel = getGuild().getChannel(message.getChannelId());
        this.sender = getGuild().getMember(message.getAuthor().getId());
        ats = new String[message.getMentions().length];
        for (int i = 0; i < message.getMentions().length; i++) {
            ats[i] = message.getMentions()[i].getId();
        }
    }

    private String[] ats;

    @Override
    public String[] getAllAt() {
        return ats;
    }
}