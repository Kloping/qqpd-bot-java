package io.github.kloping.qqbot;

import com.google.gson.Gson;
import io.github.kloping.MySpringTool.StarterObjectApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
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
    public static final Integer CODE_4006 = 4006;
    public static final Integer CODE_4007 = 4007;
    public static final Integer CODE_4008 = 4008;
    public static final Integer CODE_4009 = 4009;
    public static final Integer CODE_4900 = 4900;
    public static final Integer CODE_4913 = 4913;
    public static final Gson GSON = new Gson();
    public static final StarterObjectApplication APPLICATION = new StarterObjectApplication(Resource.class);
    public static final Set<ListenerHost> LISTENER_HOSTS = new HashSet<>();

    @AutoStand
    public static Logger logger;

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

    @AutoStand
    public static ContextManager contextManager;

    public static Future mainFuture;

    public static Starter starter;

    public static Bot bot;

    /**
     * 类型转换
     *
     * @param packet
     * @param msg
     */
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
