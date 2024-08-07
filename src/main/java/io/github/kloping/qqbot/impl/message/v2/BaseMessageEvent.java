package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.api.v2.MessageV2Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Contact;
import io.github.kloping.qqbot.utils.BaseUtils;
import lombok.Getter;

/**
 * @author github.kloping
 */
@Getter
public abstract class BaseMessageEvent<T extends Contact> implements MessageEvent<Contact, T>, MessageV2Event {
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
        return String.format("[type(%s) %s].%s:%s"
                , EnvType.GROUP.name()
                , getSubject().getId()
                , getSender().getId()
                , getRawMessage().toString0()
        );
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
}
