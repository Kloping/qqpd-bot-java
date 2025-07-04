package io.github.kloping.qqbot.network;

import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.Pack;
import io.github.kloping.qqbot.http.BotBase;
import io.github.kloping.qqbot.interfaces.OnCloseListener;
import io.github.kloping.qqbot.interfaces.OnPackReceive;
import io.github.kloping.qqbot.network.hookauth.HookAuth;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.spt.interfaces.Logger;
import io.github.kloping.spt.interfaces.component.ContextManager;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.github.kloping.qqbot.Resource.GSON;

/**
 * @author github.kloping
 */
@Entity
public class WssWorker implements Runnable {

    @AutoStand
    private ContextManager contextManager;

    @AutoStand
    private BotBase botBase;

    @AutoStand
    private Logger logger;

    @AutoStand
    HookAuth hookAuth;

    @AutoStand
    Starter.Config config;

    public WebSocketClient webSocket;

    protected Integer msgr = 0;
    protected Integer msgs = 0;

    protected URI uri;

    @Override
    public void run() {
        if (config.getWebhookport() != null && config.getWebhookport() > 0) {
            hookAuth.webhookServerStart();
            return;
        }
        try {
            try {
                if (uri == null) {
                    if (Judge.isEmpty(config.getWslink()))
                        uri = new URI(botBase.gateway().getUrl());
                    else uri = new URI(config.getWslink());
                }
            } catch (NullPointerException ex) {
                logger.error(String.format("%s Probably The APPID or TOKEN is incorrect", ex.getClass().getName()));
                return;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            logger.log("ws url:" + uri);
            if (webSocket != null && !webSocket.isClosed()) webSocket.close();
            webSocket = new WebSocketClient(uri) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    if (preMethods(serverHandshake)) return;
                    logger.info("wss opened");
                }

                @Override
                public void onMessage(String s) {
                    if (config.getWebSocketListener() != null)
                        if (!config.getWebSocketListener().onMessage(webSocket, s))
                            return;
                    Pack pack = GSON.fromJson(s, Pack.class);
                    logger.log(String.format("websocket-r: %s", s));
                    if (pack == null) {
                        logger.error(String.format("message pack parse error (%s)", s));
                    } else {
                        for (OnPackReceive onPackReceive : onPackReceives) {
                            if (onPackReceive.onReceive(pack)) break;
                        }
                    }
                    msgr++;
                }

                @Override
                public void send(String msg) throws NotYetConnectedException {
                    if (preMethods(msg)) return;
                    super.send(msg);
                    logger.log("wss send: " + msg);
                    msgs++;
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    if (preMethods(i, s, b)) return;
                    logger.waring("wss closed with code " + i + " " + s);
                    for (OnCloseListener onCloseListener : closeListeners) {
                        onCloseListener.onClose(i, webSocket);
                    }
                }

                @Override
                public void onError(Exception e) {
                    if (preMethods(e)) return;
                    logger.error("wss error");
                    e.printStackTrace();
                }
            };
            //两次心跳的时间
            webSocket.setConnectionLostTimeout(86);
            webSocket.connectBlocking(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("在WebSocketClient启动时失败");
            e.printStackTrace();
            if (!config.getReconnect()) return;
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException ex) {
                e.printStackTrace();
            }
            closeListeners.forEach(ocl -> ocl.onClose(AuthAndHeartbeat.CODE_ERROR, webSocket));
        }
    }

    public List<OnCloseListener> closeListeners = new ArrayList<>();

    public List<OnPackReceive> onPackReceives = new LinkedList<>();

    public List<OnCloseListener> getCloseListeners() {
        return closeListeners;
    }

    public List<OnPackReceive> getOnPackReceives() {
        return onPackReceives;
    }

    private boolean preMethods(Object... os0) {
        WebSocketListener listener = config.getWebSocketListener();
        if (listener == null) return false;
        Object o1 = os0[0];
        if (o1 instanceof Exception) {
            return !listener.onError(webSocket, (Exception) o1);
        } else if (o1 instanceof String) {
            return !listener.onSend(webSocket, o1.toString());
        } else if (o1 instanceof ServerHandshake) {
            return !listener.onOpen(webSocket, (ServerHandshake) o1);
        } else if (o1 instanceof Integer) {
            return !listener.onClose(webSocket, (Integer) os0[0], (String) os0[1], (boolean) os0[2]);
        } else return false;
    }
}
