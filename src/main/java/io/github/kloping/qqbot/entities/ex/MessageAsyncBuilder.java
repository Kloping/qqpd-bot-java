package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.message.Builder;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;

/**
 * 异步 消息 发送 <br>
 * 用于处理 多图片 无法同时发送的问题 <br>
 * tips：构建的{@link Image}对象仅适用 byte[] 构建方式
 *
 * @author github.kloping
 */
public class MessageAsyncBuilder implements Builder<SendAble, SendAble> {

    private final MessageChain chain = new MessageChain();


    @Override
    public MessageAsyncBuilder append(SendAble sendAble) {
        chain.append(sendAble);
        return this;
    }

    public MessageAsyncBuilder append(String text) {
        chain.append(new PlainText(text));
        return this;
    }

    @Override
    public SendAble build() {
        return chain;
    }

    public MessageAsyncBuilder at(String id) {
        return append(new At(At.MEMBER_TYPE, id));
    }

    public MessageAsyncBuilder image(String url) {
        return append(new Image(url));
    }

    public MessageAsyncBuilder image(byte[] bytes) {
        return append(new Image(bytes));
    }

    public MessageAsyncBuilder text(String text) {
        return append(new PlainText(text));
    }
}
