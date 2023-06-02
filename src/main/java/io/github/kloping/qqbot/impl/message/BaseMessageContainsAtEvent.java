package io.github.kloping.qqbot.impl.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageContainsAtEvent;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.entitys.qqpd.message.Message;

/**
 * @author github.kloping
 */
public class BaseMessageContainsAtEvent extends BaseMessageChannelReceiveEvent implements MessageContainsAtEvent {
    public BaseMessageContainsAtEvent(Message message, JSONObject jo, Bot bot) {
        super(message, jo,bot);
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