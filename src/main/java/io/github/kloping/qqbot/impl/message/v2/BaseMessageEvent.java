package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.api.v2.MessageV2Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Contact;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.Getter;

/**
 * @author github.kloping
 */
@Getter
public abstract class BaseMessageEvent implements MessageEvent<Contact, Group>, MessageV2Event {
    public BaseMessageEvent(RawMessage message, JSONObject jo, Bot bot) {
        this.bot = bot;
        this.metadata = jo;
        this.rawMessage = message;
        this.rawMessage.setEnvType(EnvType.GROUP);
        this.msgId = getMetadata().getString("id");
    }

    protected RawMessage rawMessage;
    protected Bot bot;
    protected JSONObject metadata;
    protected String msgId;

    public abstract Contact getSender();

    @Override
    public String toString() {
        return String.format("%s post %s:%s=>%s", this.getClass().getSimpleName(), getSubject().getId(), getSender().getId(), getMessage());
    }

    protected MessageChain chain;

    @Override
    public MessageChain getMessage() {
        return chain == null ? chain = BaseUtils.parseToMessageChain(getRawMessage()) : chain;
    }
}
