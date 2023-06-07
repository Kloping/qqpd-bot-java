package io.github.kloping.qqbot;

import io.github.kloping.MySpringTool.StarterObjectApplication;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.common.Public;
import io.github.kloping.qqbot.api.Intents;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.network.WssWorker;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * <h3>一般启动方式</h3>
 * <pre>{@code
 *   Starter starter = new Starter("appid", "token");
 *   starter.getConfig().setIntents(intents);
 *   starter.run();
 * }</pre>
 * </pre>
 * <h3>V1.4+ 注册监听器主机方式 [荐]</h3>
 * <pre>{@code
 *
 * starter.registerListenerHost(new ListenerHost(){
 *      @Override
 *      public void handleException(Throwable e){
 *      }
 *
 *      @EventReceiver
 *      public void onEvent(MessageChannelReceiveEvent event){
 *          event.send("测试");
 *      }
 *
 *      @EventReceiver
 *      public void onEvent(MessageDirectReceiveEvent event){
 *              event.send("测试通过");
 *      }
 * });
 * }</pre>
 * <p>
 * 可通过
 * <pre>{@code
 * starter.setReconnect(true);
 * }</pre>
 * 设置是否在断开时是否重连 默认true
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
    public static final String MAIN_FUTURE_ID = "main_future";
    public static final String RECONNECT_K_ID = "is_reconnect";
    public static final String CONFIG_ID = "config";

    public static final Integer CODE_4006 = 4006;
    public static final Integer CODE_4007 = 4007;
    public static final Integer CODE_4008 = 4008;
    public static final Integer CODE_4009 = 4009;
    public static final Integer CODE_4900 = 4900;
    public static final Integer CODE_4913 = 4913;

    private Config config = new Config();
    private WssWorker wssWorker;
    public final StarterObjectApplication APPLICATION = new StarterObjectApplication(Resource.class);
    private ContextManager contextManager;

    public Starter(String appid, String token) {
        this.getConfig().setAppid(appid);
        this.getConfig().setToken(token);
    }

    public Config getConfig() {
        return config;
    }

    @Override
    public void run() {
        APPLICATION.PRE_SCAN_RUNNABLE.add(() -> {
            APPLICATION.INSTANCE.getContextManager().append(APPLICATION.logger);
            APPLICATION.INSTANCE.getContextManager().append(APPLICATION.INSTANCE);
            APPLICATION.INSTANCE.getContextManager().append(getConfig(), CONFIG_ID);
        });
        APPLICATION.logger.setLogLevel(1);
        APPLICATION.run0(Start0.class);
        after();
    }

    protected void after() {
        String appid = getConfig().getAppid();
        String token = getConfig().getToken();
        contextManager = APPLICATION.INSTANCE.getContextManager();
        contextManager.append(this);
        contextManager.append(appid, APPID_ID);
        contextManager.append(token, TOKEN_ID);
        contextManager.append(getConfig().getCode(), INTENTS_ID);
        contextManager.append(new Integer[]{0, 1}, SHARD_ID);
        contextManager.append("Bot " + appid + "." + token, AUTH_ID);
        contextManager.append(getConfig().getReconnect(), RECONNECT_K_ID);
        wssWorker = contextManager.getContextEntity(WssWorker.class);
        wssWork();
    }

    protected void wssWork() {
        Future future = Public.EXECUTOR_SERVICE.submit(wssWorker);
        APPLICATION.INSTANCE.getContextManager().append(future, MAIN_FUTURE_ID);
    }

    public void setReconnect(Boolean reconnect) {
        getConfig().setReconnect(reconnect);
    }

    public WssWorker getWssWorker() {
        return wssWorker;
    }

    public void registerListenerHost(ListenerHost listenerHost) {
        getConfig().getListenerHosts().add(listenerHost);
    }

    @Data
    public static class Config {
        private String appid;
        private String token;
        /**
         * code 从 {@link io.github.kloping.qqbot.api.Intents#getCode }
         */
        private Integer code;
        private Boolean reconnect = true;
        private Set<ListenerHost> listenerHosts = new HashSet<>();
    }

    public Bot getBot() {
        return contextManager.getContextEntity(Bot.class);
    }
}
