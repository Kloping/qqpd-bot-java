package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.StarterObjectApplication;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.qqbot.http.*;

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
}
