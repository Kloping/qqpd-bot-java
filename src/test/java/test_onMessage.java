import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.impl.EventReceiver;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.api.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entitys.qqpd.message.Message;
import io.github.kloping.qqbot.entitys.qqpd.message.audited.MessageAudited;

/**
 * @author github.kloping
 */
public class test_onMessage {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        starter.registerListenerHost(new ListenerHost() {
            @Override
            public void handleException(Throwable e) {

            }

            @EventReceiver
            private void event(MessageEvent event) {
                Message message = event.getMessage();
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
