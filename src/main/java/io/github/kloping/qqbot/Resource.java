package io.github.kloping.qqbot;

import com.google.gson.Gson;
import io.github.kloping.MySpringTool.StarterObjectApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.qqbot.http.*;
import io.github.kloping.qqbot.impl.ListenerHost;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author github.kloping
 */
@Entity
public class Resource {
    public static final Gson GSON = new Gson();

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
}
