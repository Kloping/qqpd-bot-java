package io.github.kloping.qqbot;

import io.github.kloping.common.Public;
import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.interfaces.ImageUploadInterceptor;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.qqbot.network.WebSocketListener;
import io.github.kloping.qqbot.network.WssWorker;
import io.github.kloping.qqbot.utils.LoggerImpl;
import io.github.kloping.spt.StarterObjectApplication;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.spt.interfaces.AutomaticWiringValue;
import io.github.kloping.spt.interfaces.component.ContextManager;
import io.github.kloping.spt.interfaces.component.HttpClientManager;
import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import static io.github.kloping.spt.PartUtils.getExceptionLine;

/**
 * <h3>一般启动方式</h3>
 * <pre>{@code
 *   Starter starter = new Starter("appid", "token");
 *   starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
 *   starter.run();
 * }</pre>
 * </pre>
 * <h3>V1.4+ 注册监听器主机方式 [荐]</h3>
 * <pre>{@code
 *
 * starter.registerListenerHost(new ListenerHost(){
 * @Override
 * public void handleException(Throwable e){
 * }
 *
 * @EventReceiver
 * public void onEvent(MessageChannelReceiveEvent event){
 * event.send("测试");
 * }
 *
 * @EventReceiver
 * public void onEvent(MessageDirectReceiveEvent event){
 * event.send("测试通过");
 * }
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
    public static final String SANDBOX_NET_MAIN = "https://sandbox.api.sgroup.qq.com/";
    public static final String NET_MAIN = "https://api.sgroup.qq.com/";
    public String net = NET_MAIN;
    public static final String NET_POINT = "{io.github.kloping.qqbot.Starter.net}";
    public static final String APPID_ID = "appid";
    public static final String TOKEN_ID = "token";
    public static final String SECRET_ID = "secret";
    public static final String AUTH_ID = "appid-token";
    public static final String INTENTS_ID = "intents";
    public static final String SHARD_ID = "shard";
    public static final String PROPERTIES_ID = "properties";
    public static final String MAIN_FUTURE_ID = "main_future";
    public static final String RECONNECT_K_ID = "is_reconnect";
    public static final String CONFIG_ID = "config";


    @Getter
    private Config config = new Config();
    @Getter
    private WssWorker wssWorker;

    public final StarterObjectApplication APPLICATION = new StarterObjectApplication(Resource.class);

    private ContextManager contextManager;

    public Starter(String appid, String token) {
        this(appid, token, null);
    }

    /**
     * qq群使用必要构建方式
     *
     * @param appid
     * @param token
     * @param secret
     */
    public Starter(String appid, String token, String secret) {
        this.getConfig().setAppid(appid);
        this.getConfig().setToken(token);
        this.getConfig().setSecret(secret);
        APPLICATION.logger = LoggerImpl.INSTANCE;
    }

    @Override
    public void run() {
        APPLICATION.PRE_SCAN_RUNNABLE.add(() -> {
            APPLICATION.INSTANCE.getContextManager().append(APPLICATION.logger);
            APPLICATION.INSTANCE.getContextManager().append(APPLICATION.INSTANCE);
            APPLICATION.INSTANCE.getContextManager().append(getConfig(), CONFIG_ID);
        });
        APPLICATION.logger.setLogLevel(1);
        APPLICATION.logger.setPrefix("[qgpd-bot]");
        APPLICATION.run0(Start0.class);
        after();
    }

    protected void after() {
        String appid = getConfig().getAppid();
        String token = getConfig().getToken();
        String secret = getConfig().getSecret();
        net = getConfig().sandbox ? SANDBOX_NET_MAIN : NET_MAIN;
        contextManager = APPLICATION.INSTANCE.getContextManager();
        contextManager.append(this);
        contextManager.append(appid, APPID_ID);
        contextManager.append(token, TOKEN_ID);
        if (Judge.isNotEmpty(config.getSecret()))
            contextManager.append(secret, SECRET_ID);
        contextManager.append(getConfig().getCode(), INTENTS_ID);
        contextManager.append(new Integer[]{0, 1}, SHARD_ID);
        contextManager.append("Bot " + appid + "." + token, AUTH_ID);
        contextManager.append(getConfig().getReconnect(), RECONNECT_K_ID);
        wssWorker = contextManager.getContextEntity(WssWorker.class);
        contextManager.getContextEntity(HttpClientManager.class).setPrint(false);
        wssWork();
        Resource.print();
    }

    protected void wssWork() {
        AutomaticWiringValue automaticWiringValue = contextManager.getContextEntity(AutomaticWiringValue.class);
        if (config.getWebSocketListener() != null) {
            try {
                automaticWiringValue.wiring(config.getWebSocketListener(), contextManager);
            } catch (Exception e) {
                APPLICATION.logger.error(e.getMessage() + "\n\tat " + getExceptionLine(e));
            }
        }
        Future future = Public.EXECUTOR_SERVICE1.submit(wssWorker);
        APPLICATION.INSTANCE.getContextManager().append(future, MAIN_FUTURE_ID);
    }

    public void setReconnect(Boolean reconnect) {
        getConfig().setReconnect(reconnect);
    }

    public void registerListenerHost(ListenerHost listenerHost) {
        getConfig().getListenerHosts().add(listenerHost);
    }

    /**
     * 该类必须注解为 @{@link Entity}
     * <br> 参考 {@link io.github.kloping.qqbot.impl.registers}
     *
     * @param cla
     */
    public void registerEventsRegister(Class<? extends Events.EventRegister> cla) {
        APPLICATION.POST_SCAN_RUNNABLE.add(() -> {
            try {
                APPLICATION.INSTANCE.getClassManager().add(cla);
            } catch (Exception e) {
                APPLICATION.logger.error("An error occurred in the registration class " + cla.getSimpleName());
                APPLICATION.logger.error("\n\tat " + getExceptionLine(e));
            }
        });
    }

    @Data
    public static class Config {
        public boolean sandbox = false;
        private String appid;
        private String token;
        /**
         * 不使用v2群聊时可不设置
         */
        private String secret;
        /**
         * code 从 {@link io.github.kloping.qqbot.api.Intents#getCode }
         */
        private Integer code;
        private Boolean reconnect = true;
        private Boolean anyCloseReconnect = false;
        private String wslink = null;
        private Integer webhookport = 0;
        /**
         * webhook服务路径 默认/webhook0
         */
        private String webhookpath = "/webhook0";
        private Set<ListenerHost> listenerHosts = new HashSet<>();
        private ImageUploadInterceptor interceptor0;
        private WebSocketListener webSocketListener;

        /**
         * 在沙箱环境与正式环境 之前切换 默认正式环境
         */
        public void sandbox() {
            sandbox = !sandbox;
        }

        /**
         * 设置WebSocket链接地址 ##通常用于webhook转发 也可用于固定地址减少请求 加快启动速度
         *
         * @param wslink 配置后将不再通过/gateway请求获的地址(wss://api.sgroup.qq.com/websocket)
         */
        public void setWslink(String wslink) {
            this.wslink = wslink;
        }

        /**
         * 设置webhook服务端口 默认为0时不开启webhook
         * <br/>需要将q.qq配置链接设置为 "https://you-domain/webhook0" 路径
         * <br/>若设置此项 则 配置项 code sandbox reconnect webhookport 可能会失效
         *
         * @param webhookport
         */
        public void setWebhookport(Integer webhookport) {
            this.webhookport = webhookport;
        }
    }

    public Bot getBot() {
        return contextManager.getContextEntity(Bot.class);
    }
}
