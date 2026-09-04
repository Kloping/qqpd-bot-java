package io.github.kloping.qqbot;

import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.interfaces.FileUploadInterceptor;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.qqbot.network.WebSocketListener;
import io.github.kloping.qqbot.network.WssWorker;
import io.github.kloping.qqbot.utils.StandaloneLogging;
import io.github.kloping.spt.StarterObjectApplication;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.spt.interfaces.AutomaticWiringValue;
import io.github.kloping.spt.interfaces.component.ContextManager;
import io.github.kloping.spt.interfaces.component.HttpClientManager;
import io.github.kloping.spt.util.Judge;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * <h3>一般启动方式</h3>
 * <pre>{@code
 *   Starter starter = new Starter("appid", "secret");
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
@Slf4j
public class Starter implements Runnable {
    static {
        StandaloneLogging.configure();
    }

    public static final String SANDBOX_NET_MAIN = "https://sandbox.api.sgroup.qq.com/";
    public static final String NET_MAIN = "https://api.sgroup.qq.com/";
    public String net = NET_MAIN;
    public static final String NET_POINT = "{io.github.kloping.qqbot.Starter.net}";
    public static final String APPID_ID = "appid";
    public static final String SECRET_ID = "secret";
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
    private boolean started;

    /**
     * 使用 appid 和 secret 获取 Access Token 完成鉴权。
     *
     * @param appid 机器人 AppID
     * @param secret 机器人密钥
     */
    public Starter(String appid, String secret) {
        this.getConfig().setAppid(appid);
        this.getConfig().setSecret(secret);
    }

    @Override
    public synchronized void run() {
        if (started) {
            log.info("Bot already started, ignore duplicate start");
            return;
        }
        started = true;
        APPLICATION.PRE_SCAN_RUNNABLE.add(() -> {
            APPLICATION.INSTANCE.getContextManager().append(APPLICATION.INSTANCE);
            APPLICATION.INSTANCE.getContextManager().append(getConfig(), CONFIG_ID);
        });
        log.info("Bot starting");
        APPLICATION.run0(Start0.class);
        after();
    }

    protected void after() {
        String appid = getConfig().getAppid();
        String secret = getConfig().getSecret();
        net = getConfig().sandbox ? SANDBOX_NET_MAIN : NET_MAIN;
        contextManager = APPLICATION.INSTANCE.getContextManager();
        contextManager.append(this);
        contextManager.append(appid, APPID_ID);
        if (Judge.isNotEmpty(config.getSecret()))
            contextManager.append(secret, SECRET_ID);
        if (Judge.isNotNull(getConfig().getCode()))
          contextManager.append(getConfig().getCode(), INTENTS_ID);
        contextManager.append(new Integer[]{0, 1}, SHARD_ID);
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
                log.error("WebSocket listener wiring failed", e);
            }
        }
        Future future = config.getWebSocketExecutor().submit(wssWorker);
        APPLICATION.INSTANCE.getContextManager().append(future, MAIN_FUTURE_ID);
        log.info("WebSocket connection task submitted");
    }

    public void setReconnect(Boolean reconnect) {
        getConfig().setReconnect(reconnect);
    }

    /**
     * 关闭机器人 并释放所有资源(WebSocket连接 心跳定时任务 线程池 WebHook服务)
     * <p>
     * 调用后进程将不会再被非守护线程阻塞 可以正常退出
     * <p>
     * 注意: 该方法不可逆 关闭后不可再发送消息或接收事件 如需重启请新建 Starter 实例
     */
    public void shutdown() {
        //1. 先关闭自动重连 否则关 WebSocket 时 onClose 会触发自动重连
        getConfig().setReconnect(false);
        getConfig().setAnyCloseReconnect(false);

        //2. 取消主线程任务
        try {
            if (contextManager != null) {
                Future future = contextManager.getContextEntity(Future.class, MAIN_FUTURE_ID);
                if (future != null && !future.isCancelled() && !future.isDone()) {
                    future.cancel(true);
                }
            }
        } catch (Exception e) {
            log.error("shutdown: cancel main future failed", e);
        }

        //3. 关闭 WebSocket 连接
        try {
            if (wssWorker != null && wssWorker.webSocket != null && !wssWorker.webSocket.isClosed()) {
                wssWorker.webSocket.closeBlocking();
            }
        } catch (Exception e) {
            log.error("shutdown: close websocket failed", e);
        }

        //4. 关闭 WebHook 服务(如果开启了)
        try {
            if (contextManager != null && getConfig().getWebhookport() != null && getConfig().getWebhookport() > 0) {
                io.github.kloping.qqbot.network.hookauth.HookAuth hookAuth = contextManager.getContextEntity(io.github.kloping.qqbot.network.hookauth.HookAuth.class);
                if (hookAuth != null && hookAuth.getHttpServer() != null) {
                    hookAuth.getHttpServer().stop(0);
                }
            }
        } catch (Exception e) {
            log.error("shutdown: stop webhook server failed", e);
        }

        //5. 关闭 SDK 自己创建的线程池，用户传入的线程池由用户管理
        config.shutdownExecutors();

        log.info("Bot shutdown complete");
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
                log.error("An error occurred in the registration class {}", cla.getSimpleName(), e);
            }
        });
    }

    @Data
    public static class Config {
        public boolean sandbox = false;
        private String appid;
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
        private FileUploadInterceptor interceptor0;
        private WebSocketListener webSocketListener;
        private transient ExecutorService eventExecutor = Executors.newFixedThreadPool(8);
        private transient ExecutorService webSocketExecutor = Executors.newSingleThreadExecutor();
        private transient ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();

        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        private transient boolean eventExecutorOwned = true;

        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        private transient boolean webSocketExecutorOwned = true;

        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        private transient boolean scheduledExecutorOwned = true;

        public void setEventExecutor(ExecutorService eventExecutor) {
            if (eventExecutor == null) throw new IllegalArgumentException("eventExecutor cannot be null");
            if (this.eventExecutor == eventExecutor) return;
            if (eventExecutorOwned) this.eventExecutor.shutdownNow();
            this.eventExecutor = eventExecutor;
            this.eventExecutorOwned = false;
        }

        public void setWebSocketExecutor(ExecutorService webSocketExecutor) {
            if (webSocketExecutor == null) throw new IllegalArgumentException("webSocketExecutor cannot be null");
            if (this.webSocketExecutor == webSocketExecutor) return;
            if (webSocketExecutorOwned) this.webSocketExecutor.shutdownNow();
            this.webSocketExecutor = webSocketExecutor;
            this.webSocketExecutorOwned = false;
        }

        public void setScheduledExecutor(ScheduledExecutorService scheduledExecutor) {
            if (scheduledExecutor == null) throw new IllegalArgumentException("scheduledExecutor cannot be null");
            if (this.scheduledExecutor == scheduledExecutor) return;
            if (scheduledExecutorOwned) this.scheduledExecutor.shutdownNow();
            this.scheduledExecutor = scheduledExecutor;
            this.scheduledExecutorOwned = false;
        }

        private void shutdownExecutors() {
            if (eventExecutorOwned) eventExecutor.shutdownNow();
            if (webSocketExecutorOwned) webSocketExecutor.shutdownNow();
            if (scheduledExecutorOwned) scheduledExecutor.shutdownNow();
        }

        /**
         * 在沙箱环境与正式环境 之前切换 默认正式环境
         */
        @Deprecated
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
