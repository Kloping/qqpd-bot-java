package io.github.kloping.qqbot.patch;

import io.github.kloping.MySpringTool.annotations.Bean;
import io.github.kloping.MySpringTool.annotations.Entity;
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

    /**
     * 对 wss closed 异常的处理
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
