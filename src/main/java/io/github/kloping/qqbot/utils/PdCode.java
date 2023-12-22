package io.github.kloping.qqbot.utils;

import io.github.kloping.arr.ArrDeSerializer;
import io.github.kloping.number.NumberUtils;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.entities.ex.At;
import io.github.kloping.qqbot.entities.ex.AtAll;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.PlainText;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;

import java.util.regex.Pattern;

/**
 * @author github.kloping
 */
public class PdCode {

    public static final ArrDeSerializer<SendAble> DE_SERIALIZER = new ArrDeSerializer<>();

    public static final Pattern AT_PATTERN = Pattern.compile("<at:.*?>");
    public static final Pattern AT_ALL_PATTERN = Pattern.compile("<atAll>");
    public static final Pattern EMOJI_PATTERN = Pattern.compile("<emoji:[0-9]+>");
    public static final Pattern IMAGE_PATTERN = Pattern.compile("<image:.*?>");

    static {
        DE_SERIALIZER.add(AT_PATTERN, new ArrDeSerializer.Rule0<At>() {
            @Override
            public At deserializer(String s) {
                return new At(At.MEMBER_TYPE, NumberUtils.findNumberFromString(s));
            }
        });
        DE_SERIALIZER.add(AT_ALL_PATTERN, new ArrDeSerializer.Rule0<AtAll>() {
            @Override
            public AtAll deserializer(String s) {
                return new AtAll();
            }
        });
        DE_SERIALIZER.add(EMOJI_PATTERN, new ArrDeSerializer.Rule0<Emoji>() {
            @Override
            public Emoji deserializer(String s) {
                return Emoji.valueOf(Integer.valueOf(NumberUtils.findNumberFromString(s)));
            }
        });
        DE_SERIALIZER.add(IMAGE_PATTERN, new ArrDeSerializer.Rule0<Image>() {
            @Override
            public Image deserializer(String s) {
                return new Image(s.substring(7, s.length() - 1));
            }
        });
        DE_SERIALIZER.add(ArrDeSerializer.EMPTY_PATTERN, new ArrDeSerializer.Rule0<PlainText>() {
            @Override
            public PlainText deserializer(String s) {
                return new PlainText(s);
            }
        });
    }

    public static String serializeToPdCode(SendAble e) {
        if (e instanceof Emoji) {
            Emoji emoji = (Emoji) e;
            return (String.format("<emoji:%s>", emoji.getId()));
        } else if (e instanceof At) {
            At at = (At) e;
            return (String.format("<at:%s>", at.getTargetId()));
        } else if (e instanceof AtAll) {
            AtAll atAll = (AtAll) e;
            return ("<atAll>");
        } else if (e instanceof Image) {
            Image image = (Image) e;
            return (String.format("<image:%s>", image.getUrl().startsWith("http") ? image.getUrl() : "https://" + image.getUrl()));
        } else if (e instanceof PlainText) {
            PlainText plainText = (PlainText) e;
            return (plainText.toString());
        } else return e.toString();
    }

    public static String serializeToPdCode(SendAble[] datas) {
        StringBuilder sb = new StringBuilder();
        for (SendAble data : datas) {
            sb.append(serializeToPdCode(data));
        }
        return sb.toString();
    }

    public static String serializeToPdCode(MessageChain chain) {
        StringBuilder sb = new StringBuilder();
        chain.forEach((e) -> {
            sb.append(serializeToPdCode(e));
        });
        return sb.toString();
    }

    public static MessageChain deserializePdCode(String pdCode) {
        MessageChain chain = new MessageChain();
        for (SendAble sendAble : DE_SERIALIZER.deserializer(pdCode)) {
            chain.append(sendAble);
        }
        return chain;
    }
}
