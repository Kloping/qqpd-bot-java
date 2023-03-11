package io.github.kloping.qqbot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.common.Public;
import io.github.kloping.date.FrameUtils;
import io.github.kloping.qqbot.api.EventManager;
import io.github.kloping.qqbot.api.qqpd.message.Message;
import io.github.kloping.qqbot.entitys.Pack;
import io.github.kloping.qqbot.http.BotBase;
import io.github.kloping.qqbot.interfaces.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static io.github.kloping.qqbot.Resource.GSON;
import static io.github.kloping.qqbot.Starter.*;

/**
 * @author github.kloping
 */
@Entity
public class WssWorker implements Runnable {
    protected WebSocketClient webSocket;
    private Boolean isReconnect = true;

    @AutoStand
    ContextManager contextManager;

    public WssWorker() {
    }

    @AutoStand
    private BotBase botBase;

    private Logger logger;

    private ScheduledFuture scheduledFuture;

    public Pack jumpPack = new Pack();
    public Pack authPack = null;
    protected String sessionId = "";

    private boolean isFirst = true;
    private boolean connected = false;
    public Number heartbeatInterval;
    public int newstId = 1;

    private void init() {
        authPack = new Pack();
        authPack.setOp(2);
        JSONObject jo = new JSONObject();
        jo.put(TOKEN_ID, contextManager.getContextEntity(String.class, AUTH_ID));
        jo.put(INTENTS_ID, contextManager.getContextEntity(String.class, INTENTS_ID));
        jo.put(SHARD_ID, contextManager.getContextEntity(Integer[].class, SHARD_ID));
        jo.put(PROPERTIES_ID, new Object());
        authPack.setD(jo);
        logger = contextManager.getContextEntity(Logger.class);
    }

    @Override
    public void run() {
        try {
            init();
            URI u = new URI(botBase.gateway().getUrl());
            logger.log("ws url:" + u);
            if (webSocket != null && !webSocket.isClosed()) {
                webSocket.close();
            }
            webSocket = new WebSocketClient(u) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("wss opened");
                }

                @Override
                public void onMessage(String s) {
                    if (isFirst) {
                        logger.info("鉴权");
                        webSocket.send(JSON.toJSONString(authPack));
                        isFirst = false;
                        Pack pack = GSON.fromJson(s, Pack.class);
                        heartbeatInterval = pack.dAsMapGet("heartbeat_interval", Long.class);
                        jumpPack.setOp(1);
                        if (scheduledFuture != null && !scheduledFuture.isCancelled()) scheduledFuture.cancel(true);
                        scheduledFuture = FrameUtils.SERVICE.scheduleAtFixedRate(() -> {
                            if (newstId != -1) {
                                jumpPack.setD(newstId);
                            }
                            webSocket.send(JSON.toJSONString(jumpPack));
                        }, heartbeatInterval.longValue(), heartbeatInterval.longValue(), TimeUnit.MILLISECONDS);
                    } else {
                        Pack pack = JSON.parseObject(s, Pack.class);
                        logger.log("receive " + pack);
                        if (pack.getS() != null) {
                            newstId = pack.getS().intValue();
                        }
                        if (pack.getOp().equals(0)) {
                            sessionId = pack.dAsMapGet("session_id", String.class);
                        }
                        if (pack.getOp().equals(7)) {
                            logger.info("服务端通知客户端重新连接");
                            connected = false;
                            return;
                        }
                        if (onPackReceive != null) {
                            try {
                                onPackReceive.onReceive(pack);
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                        onReceive(pack);
                    }
                }

                @Override
                public void send(String text) throws NotYetConnectedException {
                    super.send(text);
                    logger.log("wss send: " + text);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    try {
                        logger.waring("wss closed");
                        for (OnCloseListener onCloseListener : closeListeners) {
                            onCloseListener.onReceive(i, webSocket);
                        }
                    } finally {
                        if (getReconnect() && !connected) {
                            reConnect();
                        }
                    }
                }

                @Override
                public void onError(Exception e) {
                    logger.error("wss error");
                    e.printStackTrace();
                }
            };
            webSocket.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void reConnect() {
        isFirst = true;
        authPack = new Pack();
        jumpPack = new Pack();
        if (Resource.mainFuture != null && !Resource.mainFuture.isCancelled()) {
            Resource.mainFuture.cancel(true);
        }
        Resource.mainFuture = Public.EXECUTOR_SERVICE.submit(() -> this.run());
    }

    private void onReceive(Pack pack) {
        String t = pack.getT();
        if (t == null) return;
        JSONObject jo = JSON.parseObject(JSON.toJSONString(pack.getD()));
        Public.EXECUTOR_SERVICE.submit(() -> EventManager.onEvent(t, jo));
        Message m = jo.toJavaObject(Message.class);
        switch (t) {
            case "MESSAGE_CREATE":
                Iterator<OnMessageListener> iterator0 = messageListeners.iterator();
                while (iterator0.hasNext()) {
                    iterator0.next().onMessage(m);
                }
                break;
            case "AT_MESSAGE_CREATE":
                Iterator<OnAtMessageListener> iterator1 = atMessageListeners.iterator();
                while (iterator1.hasNext()) {
                    iterator1.next().onMessage(m);
                }
                break;
            case "MESSAGE_DELETE":
                Iterator<OnMessageDeleteListener> iterator2 = messageDeleteListeners.iterator();
                while (iterator2.hasNext()) {
                    iterator2.next().onDelete(m);
                }
                break;
            case "READY":
                connected = true;
                break;
            case "DIRECT_MESSAGE_CREATE":
                break;
            default:
                Iterator<OnOtherEventListener> iterator = otherEventListeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().onEvent(t, jo);
                }
                break;
        }
    }

    public List<OnMessageListener> messageListeners = new ArrayList<>();
    public List<OnAtMessageListener> atMessageListeners = new ArrayList<>();
    public List<OnCloseListener> closeListeners = new ArrayList<>();
    public List<OnOtherEventListener> otherEventListeners = new ArrayList<>();

    public List<OnMessageDeleteListener> messageDeleteListeners = new ArrayList<>();

    private OnPackReceive onPackReceive;

    public void setOnPackReceive(OnPackReceive onPackReceive) {
        this.onPackReceive = onPackReceive;
    }

    public Boolean getReconnect() {
        return isReconnect;
    }

    public void setReconnect(Boolean reconnect) {
        isReconnect = reconnect;
    }

    public List<OnMessageListener> getMessageListeners() {
        return messageListeners;
    }

    public List<OnAtMessageListener> getAtMessageListeners() {
        return atMessageListeners;
    }

    public List<OnCloseListener> getCloseListeners() {
        return closeListeners;
    }

    public List<OnOtherEventListener> getOtherEventListeners() {
        return otherEventListeners;
    }

    public List<OnMessageDeleteListener> getMessageDeleteListeners() {
        return messageDeleteListeners;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
