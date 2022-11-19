import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.data.EventReceiver;
import io.github.kloping.qqbot.api.data.ListenerHost;
import io.github.kloping.qqbot.api.interfaces.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.api.interfaces.message.MessageEvent;
import io.github.kloping.qqbot.api.qqpd.message.Message;
import io.github.kloping.qqbot.api.qqpd.message.audited.MessageAudited;
import io.github.kloping.qqbot.interfaces.OnAtMessageListener;

/**
 * @author github.kloping
 */
public class test_onMessage {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        starter.addListener(new OnAtMessageListener() {
            @Override
            public void onMessage(Message message) {
                MessageAudited audited = message.send("回复测试");
                System.out.println(audited);
            }
        });
        starter.registerListenerHost(new ListenerHost() {
            @Override
            public void handleException(Throwable e) {

            }

            @EventReceiver
            public void onEvent(MessageEvent event) {
                event.send("测试");
            }

            @EventReceiver
            public void onEvent(MessageDirectReceiveEvent event) {
                event.send("测试通过");
            }
        });
    }
}
