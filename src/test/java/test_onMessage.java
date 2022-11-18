import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.message.Message;
import io.github.kloping.qqbot.interfaces.OnAtMessageListener;
import io.github.kloping.qqbot.api.message.audited.MessageAudited;

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
    }
}
