package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.StarterApplication;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.common.Public;
import io.github.kloping.qqbot.api.Guild;
import io.github.kloping.qqbot.api.User;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.http.GuildBase;
import io.github.kloping.qqbot.http.UserBase;
import io.github.kloping.qqbot.interfaces.OnAtMessageListener;
import io.github.kloping.qqbot.interfaces.OnMessageListener;
import io.github.kloping.qqbot.interfaces.OnPackReceive;

import java.util.HashMap;
import java.util.Map;

/**
 * @author github.kloping
 */
public class Starter implements Runnable {
    public static final String NET_MAIN = "https://api.sgroup.qq.com/";
    public static final String APPID_ID = "appid";
    public static final String TOKEN_ID = "token";
    public static final String AUTH_ID = "appid-token";
    public static final String INTENTS_ID = "intents";
    public static final String SHARD_ID = "shard";
    public static final String PROPERTIES_ID = "properties";

    private String appid;
    private String token;

    public Starter(String appid, String token) {
        this.appid = appid;
        this.token = token;
    }

    private ContextManager contextManager;

    @Override
    public void run() {
        StarterApplication.run(Start0.class);
        after();
    }

    protected void after() {
        StarterApplication.Setting.INSTANCE.getContextManager().append(this);
        StarterApplication.Setting.INSTANCE.getContextManager().append(appid, APPID_ID);
        StarterApplication.Setting.INSTANCE.getContextManager().append(token, TOKEN_ID);
        StarterApplication.Setting.INSTANCE.getContextManager().append("512", INTENTS_ID);
        StarterApplication.Setting.INSTANCE.getContextManager().append(new Integer[]{0, 1}, SHARD_ID);
        StarterApplication.Setting.INSTANCE.getContextManager().append("Bot " + appid + "." + token, AUTH_ID);
        StarterApplication.Setting.INSTANCE.getContextManager().append(StarterApplication.logger);
        contextManager = StarterApplication.Setting.INSTANCE.getContextManager();
        wssWork();
    }

    protected void wssWork() {
        Public.EXECUTOR_SERVICE.submit(() -> StarterApplication.Setting.INSTANCE.getContextManager().getContextEntity(WssWorker.class).run());
    }

    public ContextManager getContextManager() {
        return contextManager;
    }

    public void setOnPackReceive(OnPackReceive onPackReceive) {
        StarterApplication.Setting.INSTANCE.getContextManager().getContextEntity(WssWorker.class).setOnPackReceive(onPackReceive);
    }

    public void addListener(OnMessageListener listener) {
        StarterApplication.Setting.INSTANCE.getContextManager().getContextEntity(WssWorker.class).listeners.add(listener);
    }

    public void addListener(OnAtMessageListener listener) {
        StarterApplication.Setting.INSTANCE.getContextManager().getContextEntity(WssWorker.class).listeners0.add(listener);
    }

    public Bot getBot() {
        User user = StarterApplication.Setting.INSTANCE.getContextManager().getContextEntity(UserBase.class).botInfo();
        Map<String, Guild> guildMap = new HashMap<>();
        for (Guild guild : StarterApplication.Setting.INSTANCE.getContextManager().getContextEntity(GuildBase.class).getGuilds()) {
            guildMap.put(guild.getId(), guild);
        }
        return new Bot(user, guildMap);
    }
}
