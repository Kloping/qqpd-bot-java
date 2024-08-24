import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;
import io.github.kloping.qqbot.network.AuthAndHeartbeat;
import io.github.kloping.qqbot.network.WebSocketListener;
import io.github.kloping.spt.annotations.AutoStand;
import org.java_websocket.client.WebSocketClient;

/**
 * 将 wss error 加入重连
 * @author github.kloping
 */
public class test_onError implements WebSocketListener {

    @AutoStand
    AuthAndHeartbeat authAndHeartbeat;

    @AutoStand
    Starter starter;

    @Override
    public boolean onError(WebSocketClient client, Exception e) {
        //尝试重连
        authAndHeartbeat.identifyConnect(0, client);
        return WebSocketListener.super.onError(client, e);
    }

    public static void main(String[] args) throws Exception {
        Starter starter = new Starter("xxx", "xxx");
        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
        //设置websocket 监听
        starter.getConfig().setWebSocketListener(new test_onError());
        //切换沙盒
        starter.getConfig().sandbox();
        starter.run();
    }
}
