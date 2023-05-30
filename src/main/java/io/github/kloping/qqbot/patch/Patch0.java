package io.github.kloping.qqbot.patch;

import io.github.kloping.MySpringTool.annotations.AutoStand;
import io.github.kloping.MySpringTool.annotations.Bean;
import io.github.kloping.MySpringTool.annotations.Entity;
import io.github.kloping.MySpringTool.interfaces.Logger;
import io.github.kloping.common.Public;
import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.WssWorker;
import io.github.kloping.qqbot.interfaces.OnCloseListener;
import org.java_websocket.client.WebSocketClient;

import static io.github.kloping.qqbot.Resource.*;

/**
 * @author github.kloping
 */
@Entity
public class Patch0 {
    public Patch0() {

    }

    public static final Integer CODE_4006 = 4006;
    public static final Integer CODE_4007 = 4007;
    public static final Integer CODE_4008 = 4008;
    public static final Integer CODE_4009 = 4009;
    public static final Integer CODE_4900 = 4900;
    public static final Integer CODE_4913 = 4913;

    @AutoStand
    Logger logger;

    /**
     * 对 wss closed 异常的处理
     * <table><thead><tr><th style="text-align: left;">值</th> <th style="text-align: left;">含义</th> <th style="text-align: left;">是否可以重试 RESUME</th> <th style="text-align: left;">是否可以重试 IDENTIFY</th></tr></thead> <tbody><tr><td style="text-align: left;">4001</td> <td style="text-align: left;">无效的 opcode</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4002</td> <td style="text-align: left;">无效的 payload</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4007</td> <td style="text-align: left;">seq 错误</td> <td style="text-align: left;">否</td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4006</td> <td style="text-align: left;">无效的 session id，无法继续 resume，请 identify</td> <td style="text-align: left;">否</td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4008</td> <td style="text-align: left;">发送 payload 过快，请重新连接，并遵守连接后返回的频控信息</td> <td style="text-align: left;"><strong>是</strong></td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4009</td> <td style="text-align: left;">连接过期，请重连并执行 resume 进行重新连接</td> <td style="text-align: left;"><strong>是</strong></td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4010</td> <td style="text-align: left;">无效的 shard</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4011</td> <td style="text-align: left;">连接需要处理的 guild 过多，请进行合理的分片</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4012</td> <td style="text-align: left;">无效的 version</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4013</td> <td style="text-align: left;">无效的 intent</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4014</td> <td style="text-align: left;">intent 无权限</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4014</td> <td style="text-align: left;">intent 无权限</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4900~4913</td> <td style="text-align: left;">内部错误，请重连</td> <td style="text-align: left;">否</td> <td style="text-align: left;"><strong>是</strong></td></tr> <tr><td style="text-align: left;">4914</td> <td style="text-align: left;">机器人已下架,只允许连接沙箱环境,请断开连接,检验当前连接环境</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr> <tr><td style="text-align: left;">4915</td> <td style="text-align: left;">机器人已封禁,不允许连接,请断开连接,申请解封后再连接</td> <td style="text-align: left;">否</td> <td style="text-align: left;">否</td></tr></tbody></table>
     *
     * @return
     */
    @Bean
    public String m1() {
        APPLICATION.INSTANCE.getSTARTED_RUNNABLE().add(() -> {
            String token = APPLICATION.INSTANCE.getContextManager().getContextEntity(String.class, "token");
            WssWorker wssWorker = APPLICATION.INSTANCE.getContextManager().getContextEntity(WssWorker.class);
            wssWorker.getCloseListeners().add(new OnCloseListener() {
                @Override
                public void onReceive(int code, WebSocketClient wss) {
                    if (code == CODE_4006 || code == CODE_4007 || code == CODE_4008
                            || code == CODE_4009
                            || (code >= CODE_4900 && code <= CODE_4913)) {
                        wssWorker.setConnected(false);
                        identifyConnect(code, wss);
                    }
                    switch (code) {
                        case 4013:
                        case 4014:
                            logger.error("无权限订阅事件");
                            break;
                        default:
                            logger.error(String.format("暂未处理的异常code(%s)", code));
                            break;
                    }
                }

                private void identifyConnect(int code, WebSocketClient wss) {
                    if (Resource.mainFuture != null && !Resource.mainFuture.isCancelled()) {
                        Resource.mainFuture.cancel(true);
                    }
                    Resource.mainFuture = Public.EXECUTOR_SERVICE.submit(wssWorker);
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
                            "}", token, wssWorker.getSessionId(), wssWorker.newstId));
                }
            });
        });
        return "m1";
    }
}
