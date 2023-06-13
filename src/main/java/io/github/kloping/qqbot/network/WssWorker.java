package io.github.kloping.qqbot.network;

import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.qqbot.entities.Pack;
import io.github.kloping.qqbot.http.BotBase;
import io.github.kloping.qqbot.interfaces.OnCloseListener;
import io.github.kloping.qqbot.interfaces.OnPackReceive;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.github.kloping.qqbot.Resource.GSON;
import static io.github.kloping.qqbot.Starter.RECONNECT_K_ID;

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

    public WebSocketClient webSocket;

    protected Integer msgr = 0;
    protected Integer msgs = 0;

    @Override
    public void run() {
        try {
            URI u = new URI(botBase.gateway().getUrl());
            logger.log("ws url:" + u);
            if (webSocket != null && !webSocket.isClosed()) webSocket.close();
            webSocket = new WebSocketClient(u) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    logger.info("wss opened");
                }

                @Override
                public void onMessage(String s) {
                    Pack pack = GSON.fromJson(s, Pack.class);
                    logger.log(String.format("receive %s", s));
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
                public void send(String text) throws NotYetConnectedException {
                    super.send(text);
                    logger.log("wss send: " + text);
                    msgs++;
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    logger.waring("wss closed with code " + i + " " + s);
                    for (OnCloseListener onCloseListener : closeListeners) {
                        onCloseListener.onClose(i, webSocket);
                    }
                }

                @Override
                public void onError(Exception e) {
                    logger.error("wss error");
                    e.printStackTrace();
                }
            };
            //两次心跳的事件
            webSocket.setConnectionLostTimeout(120);
            webSocket.run();
        } catch (Exception e) {
            logger.error("在WebSocketClient启动时失败");
            e.printStackTrace();
        }
    }

    public List<OnCloseListener> closeListeners = new ArrayList<>();
    public List<OnPackReceive> onPackReceives = new LinkedList<>();

    public Boolean getReconnect() {
        Boolean k = contextManager.getContextEntity(Boolean.class, RECONNECT_K_ID);
        return k == null ? false : k;
    }

    public List<OnCloseListener> getCloseListeners() {
        return closeListeners;
    }

    public List<OnPackReceive> getOnPackReceives() {
        return onPackReceives;
    }
}
