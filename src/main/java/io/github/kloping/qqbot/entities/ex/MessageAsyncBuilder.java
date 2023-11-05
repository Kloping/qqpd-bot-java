package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.message.Builder;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.http.data.ActionResult;

import java.util.LinkedList;
import java.util.List;

/**
 * 异步 消息 发送 <br>
 * 用于处理 多图片 无法同时发送的问题 <br>
 * tips：构建的{@link Image}对象仅适用 byte[] 构建方式
 *
 * @author github.kloping
 */
public class MessageAsyncBuilder implements Builder<SendAble, SendAble> {

    @Override
    public MessageAsyncBuilder append(SendAble sendAble) {
        list.add(sendAble);
        return this;
    }

    private List<SendAble> list = new LinkedList<>();


    @Override
    public SendAble build() {
        return new SendAble() {
            @Override
            public ActionResult send(SenderAndCidMidGetter er) {
                MessagePreBuilder builder = new MessagePreBuilder();
                if (message != null) builder.reply(message);
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
            }
        };
    }

    private RawMessage message;

    public MessageAsyncBuilder reply(RawMessage message) {
        this.message = message;
        return this;
    }
}
