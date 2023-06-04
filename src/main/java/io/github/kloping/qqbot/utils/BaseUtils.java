package io.github.kloping.qqbot.utils;

import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.entities.qqpd.message.MessageReference;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.impl.MessagePacket;

import java.util.Map;

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
}
