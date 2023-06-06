package io.github.kloping.qqbot.entities.ex.msg;

import io.github.kloping.object.ObjectUtils;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.ex.*;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.http.data.ActionResult;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author github.kloping
 */
public class MessageChain implements SendAble {

    public List<SendAble> list = new LinkedList<>();

    @Override
    public ActionResult send(SenderAndCidMidGetter er) {
        MessageBuilder builder = new MessageBuilder();
        for (SendAble sendAble : list) append(sendAble, builder);
        return builder.build().send(er);
    }

    public void forEach(Consumer<? super SendAble> consumer) {
        for (SendAble data : list) {
            consumer.accept(data);
        }
    }

    public MessageChain append(SendAble sendAble) {
        if (sendAble != null)
            list.add(sendAble);
        return this;
    }

    private void append(SendAble sendAble, MessageBuilder builder) {
        if (sendAble instanceof At) {
            builder.append((At) sendAble);
        } else if (sendAble instanceof AtAll) {
            builder.append((AtAll) sendAble);
        } else if (sendAble instanceof Image) {
            builder.append((Image) sendAble);
        } else if (sendAble instanceof PlainText) {
            builder.append(((PlainText) sendAble).getText());
        } else if (sendAble instanceof Emoji) {
        }
    }

    public int size() {
        return list.size();
    }

    public SendAble get(int index) {
        return list.get(index);
    }

    public boolean isType(int index, Class<? extends SendAble> cla) {
        return ObjectUtils.isSuperOrInterface(cla, list.get(index).getClass());
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
