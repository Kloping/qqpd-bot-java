import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.message.Message;
import io.github.kloping.qqbot.interfaces.OnMessageListener;

/**
 * @author github.kloping
 */
public class test_onMessage {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        starter.addMessageListener(new OnMessageListener() {
            @Override
            public void onMessage(Message message) {
                System.out.println(message);
            }
        });
    }
}
