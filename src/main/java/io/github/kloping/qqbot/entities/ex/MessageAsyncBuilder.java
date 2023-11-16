package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.message.Builder;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

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
        chain.append(sendAble);
        return this;
    }

    public MessageAsyncBuilder append(String text) {
        chain.append(new PlainText(text));
        return this;
    }

    private MessageChain chain = new MessageChain();

    @Override
    public SendAble build() {
        return chain;
    }

    private RawMessage message;

    public MessageAsyncBuilder reply(RawMessage message) {
        this.message = message;
        return this;
    }
}
