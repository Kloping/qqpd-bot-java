package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.StarterObjectApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.api.data.ListenerHost;
import io.github.kloping.qqbot.api.data.MessagePacket;
import io.github.kloping.qqbot.api.qqpd.message.MessageReference;
import io.github.kloping.qqbot.api.qqpd.message.PreMessage;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.http.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author github.kloping
 */
@Entity
public class Resource {
    public static final StarterObjectApplication APPLICATION = new StarterObjectApplication();
    public static final Set<ListenerHost> LISTENER_HOSTS = new HashSet<>();

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

    @AutoStand
    public static DmsBase dmsBase;

    public static Future mainFuture;

    public static Starter starter;

    public static Bot bot;


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
