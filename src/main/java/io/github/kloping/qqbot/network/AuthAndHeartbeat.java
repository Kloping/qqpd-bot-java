package io.github.kloping.qqbot.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.AutoStandAfter;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.MySpringTool.interfaces.component.ContextManager;
import io.github.kloping.common.Public;
import io.github.kloping.date.FrameUtils;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.Pack;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.BaseConnectedEvent;
import io.github.kloping.qqbot.interfaces.OnCloseListener;
import io.github.kloping.qqbot.interfaces.OnPackReceive;
import org.java_websocket.client.WebSocketClient;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static io.github.kloping.qqbot.Starter.*;

/**
 * @author github.kloping
 */
@Entity
public class AuthAndHeartbeat implements OnPackReceive, OnCloseListener, Events.EventRegister {
    @AutoStand
    Logger logger;

    @AutoStand
    ContextManager contextManager;

    @AutoStand
    private WssWorker wssWorker;

    @AutoStandAfter
    private void r0(WssWorker wssWorker) {
        wssWorker.getOnPackReceives().add(this);
        wssWorker.getCloseListeners().add(this);
    }

    /**
     * 对 wss closed 异常的处理
     * <table><thead><tr><th style="text-align: left;">值</th> <th style="text-align: left;">含义</th> <th style="text-align: left;">是否可以重试 RESUME</th> <th style="text-align: left;">是否可以重试 IDENTIFY</th></tr></thead> <tbody><tr><td style="text-align: left;">4001</td> <td style="text-align: left;">无效的 opcode</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4002</td> <td style="text-align: left;">无效的 payload</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4007</td> <td style="text-align: left;">seq 错误</td> <td style="text-align: left;">否</td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4006</td> <td style="text-align: left;">无效的 session id，无法继续 resume，请 identify</td> <td style="text-align: left;">否</td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4008</td> <td style="text-align: left;">发送 payload 过快，请重新连接，并遵守连接后返回的频控信息</td> <td style="text-align: left;"><strong>是</strong></td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4009</td> <td style="text-align: left;">连接过期，请重连并执行 resume 进行重新连接</td> <td style="text-align: left;"><strong>是</strong></td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4010</td> <td style="text-align: left;">无效的 shard</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4011</td> <td style="text-align: left;">连接需要处理的 guild 过多，请进行合理的分片</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4012</td> <td style="text-align: left;">无效的 version</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4013</td> <td style="text-align: left;">无效的 intent</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4014</td> <td style="text-align: left;">intent 无权限</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4014</td> <td style="text-align: left;">intent 无权限</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4900~4913</td> <td style="text-align: left;">内部错误，请重连</td> <td style="text-align: left;">否</td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4914</td> <td style="text-align: left;">机器人已下架,只允许连接沙箱环境,请断开连接,检验当前连接环境</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4915</td> <td style="text-align: left;">机器人已封禁,不允许连接,请断开连接,申请解封后再连接</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr></tbody></table>
     *
     * @return
     */
    @Override
    public void onClose(int code, WebSocketClient wss) {
        if (code == CODE_4006 || code == CODE_4007 || code == CODE_4008 || code == CODE_4009 || (code >= CODE_4900 && code <= CODE_4913)) {
            identifyConnect(code, wss);
        } else switch (code) {
            case 4013:
            case 4014:
                logger.error("无权限订阅事件");
                break;
            case 1006:
                // wss closed with code 1006 The connection was closed
                // because the other endpoint did not respond with a pong in time.
                // For more information check:
                // https://github.com/TooTallNate/Java-WebSocket/wiki/Lost-connection-detection
                identifyConnect(code, wss);
                break;
            default:
                logger.error(String.format("暂未处理的异常code(%s)", code));
                break;
        }

    }

    @AutoStand
    Config config;

    private void identifyConnect(int code, WebSocketClient wss) {
        if (!config.getReconnect()) return;
        Future future = contextManager.getContextEntity(Future.class, Starter.MAIN_FUTURE_ID);
        if (future != null && !future.isCancelled()) {
            future.cancel(true);
        }
        wssWorker.msgr = 0;
        wssWorker.msgs = 0;
        future = Public.EXECUTOR_SERVICE.submit(wssWorker);
        contextManager.append(future, Starter.MAIN_FUTURE_ID);
    }

    private void resumeConnect(int code, WebSocketClient wss) {
        wss.reconnect();
        wss.send(String.format("{\n" +
                "  \"op\": 6,\n" +
                "  \"d\": {\n" +
                "    \"token\": \"%s\",\n" +
                "    \"session_id\": \"%s\",\n" +
                "    \"seq\": %s\n" +
                "  }\n" +
                "}", contextManager.getContextEntity(String.class, TOKEN_ID), sessionId, newstId));
    }

    private Pack jumpPack = null;
    private Pack authPack = null;
    protected String sessionId = "";

    protected int newstId = 1;

    private Number heartbeatInterval;
    private ScheduledFuture scheduledFuture;

    @Override
    public boolean onReceive(Pack pack) {
        if (wssWorker.msgr == 0) {
            logger.info("Authentication");
            authPack = new Pack();
            authPack.setOp(2);
            JSONObject jo = new JSONObject();
            jo.put(TOKEN_ID, contextManager.getContextEntity(String.class, AUTH_ID));
            jo.put(INTENTS_ID, contextManager.getContextEntity(Integer.class, INTENTS_ID));
            jo.put(SHARD_ID, contextManager.getContextEntity(Integer[].class, SHARD_ID));
            jo.put(PROPERTIES_ID, new Object());
            authPack.setD(jo);
            wssWorker.webSocket.send(JSON.toJSONString(authPack));

            heartbeatInterval = pack.dAsMapGet("heartbeat_interval", Long.class);
            jumpPack = new Pack();
            jumpPack.setOp(1);
            if (scheduledFuture != null && !scheduledFuture.isCancelled()) scheduledFuture.cancel(true);
            scheduledFuture = FrameUtils.SERVICE.scheduleAtFixedRate(() -> {
                if (newstId != -1) {
                    jumpPack.setD(newstId);
                }
                wssWorker.webSocket.send(JSON.toJSONString(jumpPack));
            }, heartbeatInterval.longValue(), heartbeatInterval.longValue(), TimeUnit.MILLISECONDS);
            return true;
        } else if (pack.getOp() == 7) {
            logger.waring("op 7 Reconnect");
        }
        if (pack.getS() != null) {
            newstId = pack.getS().intValue();
        }
        return false;
    }

    @AutoStandAfter
    private void r0(Events events) {
        events.register("READY", this);
    }

    @AutoStand
    Bot bot;

    @Override
    public Event handle(String t, JSONObject mateData, RawMessage message) {
        sessionId = mateData.getString("session_id");
        logger.info("Ready!");
        return new BaseConnectedEvent(mateData, bot, sessionId);
    }
}
