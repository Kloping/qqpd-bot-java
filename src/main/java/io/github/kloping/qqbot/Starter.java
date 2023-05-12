package io.github.kloping.qqbot;

import io.github.kloping.common.Public;
import io.github.kloping.qqbot.api.data.ListenerHost;
import io.github.kloping.qqbot.api.qqpd.Guild;
import io.github.kloping.qqbot.api.qqpd.User;
import io.github.kloping.qqbot.entitys.Bot;
import io.github.kloping.qqbot.interfaces.*;

import java.util.HashMap;
import java.util.Map;

import static io.github.kloping.qqbot.Resource.APPLICATION;
import static io.github.kloping.qqbot.Resource.bot;

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
        Resource.starter = this;
    }

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
        //22.11.18 修改为默认监听所有事件 by kloping
        APPLICATION.INSTANCE.getContextManager().append("2081166851", INTENTS_ID);
        APPLICATION.INSTANCE.getContextManager().append(new Integer[]{0, 1}, SHARD_ID);
        APPLICATION.INSTANCE.getContextManager().append("Bot " + appid + "." + token, AUTH_ID);
        APPLICATION.INSTANCE.getContextManager().append(APPLICATION.logger);
        Resource.contextManager = APPLICATION.INSTANCE.getContextManager();
        wssWorker = APPLICATION.INSTANCE.getContextManager().getContextEntity(WssWorker.class);
        wssWork();
    }

    protected void wssWork() {
        Resource.mainFuture = Public.EXECUTOR_SERVICE.submit(() -> wssWorker.run());
    }

    public void setOnPackReceive(OnPackReceive onPackReceive) {
        wssWorker.setOnPackReceive(onPackReceive);
    }

    public void addListener(OnMessageListener listener) {
        wssWorker.getMessageListeners().add(listener);
    }

    public void addListener(OnAtMessageListener listener) {
        wssWorker.getAtMessageListeners().add(listener);
    }

    public void addListener(OnOtherEventListener listener) {
        wssWorker.getOtherEventListeners().add(listener);
    }

    public void addListener(OnMessageDeleteListener listener) {
        wssWorker.getMessageDeleteListeners().add(listener);
    }

    public Bot getBot() {
        if (Resource.bot == null) {
            User user = Resource.userBase.botInfo();
            Map<String, Guild> guildMap = new HashMap<>();
            for (Guild guild : Resource.guildBase.getGuilds()) {
                guildMap.put(guild.getId(), guild);
            }
            return bot = new Bot(user, guildMap);
        } else {
            return bot;
        }
    }

    public WssWorker getWssWorker() {
        return wssWorker;
    }

    public void registerListenerHost(ListenerHost listenerHost) {
        Resource.LISTENER_HOSTS.add(listenerHost);
    }
}
