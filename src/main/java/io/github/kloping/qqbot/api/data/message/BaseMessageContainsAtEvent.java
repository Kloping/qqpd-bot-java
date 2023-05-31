package io.github.kloping.qqbot.api.data.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.interfaces.message.MessageContainsAtEvent;
import io.github.kloping.qqbot.api.qqpd.message.Message;

/**
 * @author github.kloping
 */
public class BaseMessageContainsAtEvent extends BaseMessageChannelReceiveEvent implements MessageContainsAtEvent {
    public BaseMessageContainsAtEvent(Message message, JSONObject jo) {
        super(message, jo);
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