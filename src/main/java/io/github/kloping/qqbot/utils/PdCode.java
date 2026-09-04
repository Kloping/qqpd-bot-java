package io.github.kloping.qqbot.utils;

import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.entities.ex.At;
import io.github.kloping.qqbot.entities.ex.AtAll;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.PlainText;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author github.kloping
 */
public class PdCode {

    private static final Pattern TOKEN_PATTERN = Pattern.compile(
            "<at:([^>]+)>|<atAll\\s*/?>|<emoji:(\\d+)>|<image:([^>]+)>|<@!?([^>\\s]+)>|<#([0-9]+)>|" +
                    "<qqbot-at-user\\s+id\\s*=\\s*\"([^\"]+)\"\\s*/?>|<faceType\\s*=\\s*([^>]+)>|" +
                    "@everyone|<qqbot-at-everyone\\s*/?>");
    private static final Pattern FACE_ID_PATTERN = Pattern.compile("\\bfaceId\\s*=\\s*\"?([0-9]+)\"?", Pattern.CASE_INSENSITIVE);

    public static String serializeToPdCode(SendAble e) {
        if (e == null) return "";
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
            String url = image.getUrl();
            return url == null ? "" : String.format("<image:%s>", url.startsWith("http") ? url : "https://" + url);
        } else if (e instanceof PlainText) {
            PlainText plainText = (PlainText) e;
            return (plainText.toString());
        } else return e.toString();
    }

    public static String serializeToPdCode(SendAble[] datas) {
        if (datas == null) return "";
        StringBuilder sb = new StringBuilder();
        for (SendAble data : datas) {
            sb.append(serializeToPdCode(data));
        }
        return sb.toString();
    }

    public static String serializeToPdCode(MessageChain chain) {
        if (chain == null) return "";
        StringBuilder sb = new StringBuilder();
        chain.forEach((e) -> {
            sb.append(serializeToPdCode(e));
        });
        return sb.toString();
    }

    public static MessageChain deserializePdCode(String pdCode) {
        MessageChain chain = new MessageChain();
        if (pdCode == null || pdCode.isEmpty()) return chain;

        Matcher matcher = TOKEN_PATTERN.matcher(pdCode);
        int end = 0;
        while (matcher.find()) {
            appendText(chain, pdCode.substring(end, matcher.start()));
            SendAble sendAble = deserializeToken(matcher);
            if (sendAble == null) appendText(chain, matcher.group());
            else chain.append(sendAble);
            end = matcher.end();
        }
        appendText(chain, pdCode.substring(end));
        return chain;
    }

    private static void appendText(MessageChain chain, String text) {
        if (!text.isEmpty()) chain.append(new PlainText(text));
    }

    private static SendAble deserializeToken(Matcher matcher) {
        if (matcher.group(1) != null) return new At(At.MEMBER_TYPE, matcher.group(1));
        if (matcher.group(2) != null) return parseEmoji(matcher.group(2));
        if (matcher.group(3) != null) return new Image(matcher.group(3));
        if (matcher.group(4) != null) return new At(At.MEMBER_TYPE, matcher.group(4));
        if (matcher.group(5) != null) return new At(At.CHANNEL_TYPE, matcher.group(5));
        if (matcher.group(6) != null) return new At(At.MEMBER_TYPE, matcher.group(6));
        if (matcher.group(7) != null) {
            Matcher faceId = FACE_ID_PATTERN.matcher(matcher.group(7));
            if (faceId.find()) return parseEmoji(faceId.group(1));
            return null;
        }
        String token = matcher.group();
        if (token.startsWith("<atAll") || "@everyone".equals(token) || token.startsWith("<qqbot-at-everyone")) {
            return new AtAll();
        }
        return null;
    }

    private static SendAble parseEmoji(String id) {
        try {
            return Emoji.valueOf(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
