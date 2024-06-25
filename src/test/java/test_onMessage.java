import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.api.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.ex.At;
import io.github.kloping.qqbot.entities.ex.AtAll;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.PlainText;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.ListenerHost;

/**
 * @author github.kloping
 */
public class test_onMessage {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        starter.registerListenerHost(new ListenerHost() {

            @EventReceiver
            private void event(MessageEvent event) {
                RawMessage message = event.getRawMessage();
                message.send("回复测试");
            }
        });
        starter.registerListenerHost(new ListenerHost() {

            @EventReceiver
            public void onEvent(MessageEvent event) {
                event.send("测试");
            }

            @EventReceiver
            public void onEvent(MessageDirectReceiveEvent event) {
                event.send("测试通过");
            }

            @EventReceiver
            public void onMessage(MessageChannelReceiveEvent event) {
                //消息类型遍历
                MessageChain chain = event.getMessage();
                chain.forEach((e) -> {
                    if (e instanceof Emoji) {
                        Emoji emoji = (Emoji) e;
                    } else if (e instanceof At) {
                        At at = (At) e;
                    } else if (e instanceof AtAll) {
                        AtAll atAll = (AtAll) e;
                    } else if (e instanceof Image) {
                        Image image = (Image) e;
                    } else if (e instanceof PlainText) {
                        PlainText plainText = (PlainText) e;
                    }
                });
            }
        });
    }
}
