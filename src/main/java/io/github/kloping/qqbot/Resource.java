package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.StarterObjectApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.api.message.MessagePacket;
import io.github.kloping.qqbot.api.message.MessageReference;
import io.github.kloping.qqbot.api.message.PreMessage;
import io.github.kloping.qqbot.http.*;

import java.util.concurrent.Future;

/**
 * @author github.kloping
 */
@Entity
public class Resource {
    public static final StarterObjectApplication APPLICATION = new StarterObjectApplication();

    @AutoStand
    public static BotBase botBase;

    @AutoStand
    public static GuildBase guildBase;

    @AutoStand
    public static MemberBase memberBase;

    @AutoStand
    public static MessageBase messageBase;

    @AutoStand
    public static UserBase userBase;

    public static Future mainFuture;

    public static void packet2pre(MessagePacket packet, PreMessage msg) {
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
}
