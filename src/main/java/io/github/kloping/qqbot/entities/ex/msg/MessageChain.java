package io.github.kloping.qqbot.entities.ex.msg;

import io.github.kloping.object.ObjectUtils;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.entities.ex.*;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.http.data.Result;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author github.kloping
 */
public class MessageChain implements SendAble {

    public List<SendAble> list = new LinkedList<>();

    @Override
    public Result send(SenderAndCidMidGetter er) {
        if (er.getEnvType() == EnvType.GUILD) {
            MessagePreBuilder builder = new MessagePreBuilder();
            boolean flag0 = false;
            for (SendAble sendAble : list) {
                if (sendAble instanceof PlainText) {
                    builder.append(sendAble.toString());
                } else if (sendAble instanceof Image) {
                    if (!flag0) {
                        builder.append((Image) sendAble);
                        flag0 = true;
                    } else {
                        er.send(builder.build());
                        builder.clear();
                        builder.append((Image) sendAble);
                    }
                } else if (sendAble instanceof At) {
                    builder.append((At) sendAble);
                } else if (sendAble instanceof AtAll) {
                    builder.append((AtAll) sendAble);
                } else if (sendAble instanceof Emoji) {
                    builder.append((Emoji) sendAble);
                }
            }
            return er.send(builder.build());
        } else {
            StringBuilder result = new StringBuilder();
            StringBuilder sb = new StringBuilder();
            int seq = 1;
            for (int i = 0; i < list.size(); i++) {
                SendAble e = list.get(i);
                if (e instanceof Image) {
                    result.append("\n").append(er.send((Image) e).getData());
                } else if (e instanceof MessageChain) {
                    ((MessageChain) e).forEach((e1) -> {
                        result.append("\n").append(er.send(e1));
                    });
                } else {
                    sb.append(e.toString());
                }
            }
            if (sb.length() > 0) result.append("\n").append(er.send(sb.toString()).getData());
            return new Result<String>(result.toString().trim());
        }
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

    private void append(SendAble sendAble, MessagePreBuilder builder) {
        if (sendAble instanceof At) {
            builder.append((At) sendAble);
        } else if (sendAble instanceof AtAll) {
            builder.append((AtAll) sendAble);
        } else if (sendAble instanceof Image) {
            builder.append((Image) sendAble);
        } else if (sendAble instanceof PlainText) {
            builder.append(((PlainText) sendAble).getText());
        } else if (sendAble instanceof Emoji) {
            builder.append((Emoji) sendAble);
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
