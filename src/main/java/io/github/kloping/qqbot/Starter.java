package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.StarterApplication;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.common.Public;
import io.github.kloping.qqbot.api.Guild;
import io.github.kloping.qqbot.api.User;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.interfaces.OnAtMessageListener;
import io.github.kloping.qqbot.interfaces.OnMessageListener;
import io.github.kloping.qqbot.interfaces.OnPackReceive;

import java.util.HashMap;
import java.util.Map;

import static io.github.kloping.qqbot.Resource.APPLICATION;

/**
 * <h3>一般启动方式</h3>
 * <pre>{@code
 *   Starter starter = new Starter("appid", "token");
 *   starter.run();
 * }</pre>
 * <h3>注册监听器</h3>
 * <pre>{@code
 * starter.addListener(new OnAtMessageListener() {
 *      @Override
 *      public void onMessage(Message message) {
 *          //基于接收的At消息 发送消息
 *          message.send("消息内容");
 *      }
 * });
 * }
 * </pre>
 * <h3>目前存在过段时间wss断开连接的问题!</h3>
 * 可通过
 * <pre>{@code
 * starter.getWssWorker().setReconnect(boolean);
 * }</pre>
 * 设置是否在断开时是否重连 默认false
 * <br>
 * <br>
 * <hr>
 * <br>
 * <h2>更多文档请待后续版本或 参考<a href="https://bot.q.qq.com/wiki/develop/api/">QQ频道官方文档</a></h2>
 * <hr>
 * <br>
 *
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
    private WssWorker wssWorker;

    @Override
    public void run() {
        APPLICATION.run0(Start0.class);
        after();
    }

    protected void after() {
        APPLICATION.INSTANCE.getContextManager().append(this);
        APPLICATION.INSTANCE.getContextManager().append(appid, APPID_ID);
        APPLICATION.INSTANCE.getContextManager().append(token, TOKEN_ID);
        APPLICATION.INSTANCE.getContextManager().append("512", INTENTS_ID);
        APPLICATION.INSTANCE.getContextManager().append(new Integer[]{0, 1}, SHARD_ID);
        APPLICATION.INSTANCE.getContextManager().append("Bot " + appid + "." + token, AUTH_ID);
        APPLICATION.INSTANCE.getContextManager().append(StarterApplication.logger);
        contextManager = APPLICATION.INSTANCE.getContextManager();
        wssWorker = APPLICATION.INSTANCE.getContextManager().getContextEntity(WssWorker.class);
        wssWork();
    }

    protected void wssWork() {
        Public.EXECUTOR_SERVICE.submit(() -> wssWorker.run());
    }

    public ContextManager getContextManager() {
        return contextManager;
    }

    public void setOnPackReceive(OnPackReceive onPackReceive) {
        wssWorker.setOnPackReceive(onPackReceive);
    }

    public void addListener(OnMessageListener listener) {
        wssWorker.messageListeners.add(listener);
    }

    public void addListener(OnAtMessageListener listener) {
        wssWorker.atMessageListeners.add(listener);
    }

    public Bot getBot() {
        User user = Resource.userBase.botInfo();
        Map<String, Guild> guildMap = new HashMap<>();
        for (Guild guild : Resource.guildBase.getGuilds()) {
            guildMap.put(guild.getId(), guild);
        }
        return new Bot(user, guildMap);
    }

    public WssWorker getWssWorker() {
        return wssWorker;
    }
}
