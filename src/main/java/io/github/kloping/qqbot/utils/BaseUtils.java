package io.github.kloping.qqbot.utils;

import io.github.kloping.arr.ArrDeSerializer;
import io.github.kloping.judge.Judge;
import io.github.kloping.number.NumberUtils;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.entities.ex.At;
import io.github.kloping.qqbot.entities.ex.AtAll;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.PlainText;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.entities.qqpd.message.MessageAttachment;
import io.github.kloping.qqbot.entities.qqpd.message.MessageReference;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.impl.MessagePacket;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author github.kloping
 */
public class BaseUtils {

    /**
     * 类型转换
     *
     * @param packet
     * @param msg
     */
    public static void packet2pre(MessagePacket packet, RawPreMessage msg) {
        if (Judge.isNotEmpty(packet.getContent())) {
            msg.setContent(packet.getContent());
        }
        if (Judge.isNotEmpty(packet.getImage())) {
            msg.setImage(packet.getImage());
        }
        if (Judge.isNotEmpty(packet.getImage())) {
            msg.setImage(packet.getImage());
        }
        if (Judge.isNotEmpty(packet.getReplyId())) {
            msg.setMessageReference(new MessageReference(packet.getReplyId()));
        }
    }

    public static <T, K1, K2> T tryGet(Map<K1, Map<K2, T>> tmap, K1 k1, K2 k2) {
        Map<K2, T> map = tmap.get(k1);
        if (map == null) return null;
        else return map.get(k2);
    }

    public static final Pattern AT_PATTERN = Pattern.compile("<@![0-9]+>");
    public static final Pattern AT_ALL = Pattern.compile("@everyone");
    public static final Pattern AT_CHANNEL = Pattern.compile("<#[0-9]+>");
    public static final Pattern EMOJI = Pattern.compile("<emoji:[0-9]+>");

    public static MessageChain parseToMessageChain(RawMessage rawMessage) {
        String content = rawMessage.getContent();
        MessageChain chain = new MessageChain();
        dispose(content, chain);
        if (rawMessage.getAttachments() != null) {
            for (MessageAttachment attachment : rawMessage.getAttachments()) {
                chain.append(new Image(attachment.getUrl()));
            }
        }
        return chain;
    }

    public static final ArrDeSerializer<SendAble> DE_SERIALIZER = new ArrDeSerializer<>();

    static {
        DE_SERIALIZER.add(AT_PATTERN, new ArrDeSerializer.Rule0<At>() {
            @Override
            public At deserializer(String s) {
                return new At(At.MEMBER_TYPE, NumberUtils.findNumberFromString(s));
            }
        });
        DE_SERIALIZER.add(AT_ALL, new ArrDeSerializer.Rule0<AtAll>() {
            @Override
            public AtAll deserializer(String s) {
                return new AtAll();
            }
        });
        DE_SERIALIZER.add(AT_CHANNEL, new ArrDeSerializer.Rule0<At>() {
            @Override
            public At deserializer(String s) {
                return new At(At.CHANNEL_TYPE, NumberUtils.findNumberFromString(s));
            }
        });
        DE_SERIALIZER.add(EMOJI, new ArrDeSerializer.Rule0<Emoji>() {
            @Override
            public Emoji deserializer(String s) {
                return Emoji.valueOf(Integer.valueOf(NumberUtils.findNumberFromString(s)));
            }
        });
        //============
        DE_SERIALIZER.add(ArrDeSerializer.EMPTY_PATTERN, new ArrDeSerializer.Rule0<PlainText>() {
            @Override
            public PlainText deserializer(String s) {
                return new PlainText(s);
            }
        });
    }

    private static void dispose(String content, MessageChain chain) {
        List<SendAble> sendAbles = DE_SERIALIZER.deserializer(content);
        for (SendAble sendAble : sendAbles) {
            chain.append(sendAble);
        }
    }
}
