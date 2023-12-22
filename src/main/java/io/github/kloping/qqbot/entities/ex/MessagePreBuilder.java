package io.github.kloping.qqbot.entities.ex;

import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import lombok.Getter;

/**
 * @author github.kloping
 */
public class MessagePreBuilder {
    private MessagePre pre = new MessagePre();
    @Getter
    private boolean empty = true;
    public MessagePreBuilder append(String text) {
        pre.setContent(pre.getContent() + text);
        empty = false;
        return this;
    }

    public MessagePreBuilder append(Image image) {
        pre.setImage(image);
        empty = false;
        return this;
    }

    public MessagePreBuilder append(At at) {
        return append(at.toString());
    }

    public MessagePreBuilder append(AtAll at) {
        return append("@everyone");
    }

    public MessagePreBuilder append(Emoji emoji) {
        return append(emoji.toString0());
    }

    public MessagePreBuilder reply(RawMessage message) {
        pre.setReplyId(message.getId());
        return this;
    }

    public MessagePre build() {
        return pre;
    }

    public void clear() {
        pre.setContent("");
        empty = true;
    }
}
