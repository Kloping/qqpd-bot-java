import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.event.ConnectedEvent;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.network.Events;
import io.github.kloping.spt.annotations.AutoStand;
import io.github.kloping.spt.annotations.AutoStandAfter;
import io.github.kloping.spt.annotations.Entity;
import io.github.kloping.spt.interfaces.Logger;
import org.junit.Test;

/**
 * @author github.kloping
 */
public class EventsRegisterTest extends ListenerHost {
    private Starter starter;
    private Logger logger;
    @Test
    public void testBefore() throws Throwable {
//        String appid = System.getProperty("appid");
//        String token = System.getProperty("token");
//        starter = new Starter(appid, token);
//        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
//        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
//        starter.setReconnect(true);
//        starter.registerListenerHost(this);
//        test0();
//        starter.run();
//        logger = starter.APPLICATION.logger;
//        TimeUnit.SECONDS.sleep(8);
        System.out.println("测试通过.");
    }

    @Entity
    public static class TestEventsRegister implements Events.EventRegister {

        public static final String TEST_EVENT = "TEST_EVENT";

        @AutoStandAfter
        private void r8(Events events) {
            events.register(TEST_EVENT, this);
        }

        @AutoStand
        Bot bot;

        @Override
        public Event handle(String t, JSONObject mateData, RawMessage message) {
            if (TEST_EVENT.equals(TEST_EVENT)) {
                bot.logger.info("=====================================");
                bot.logger.waring(TEST_EVENT + " handle for raw " + mateData);
            }
            return null;
        }
    }

    private void test0() {
        starter.registerEventsRegister(TestEventsRegister.class);
    }

    @EventReceiver
    public void online(ConnectedEvent event) {
        logger.info("bot online start test");
        test();
    }

    public void test() {
        starter.getWssWorker().webSocket
                .onMessage("{\n" +
                        "  \"op\": 0,\n" +
                        "  \"s\": 3,\n" +
                        "  \"t\": \"TEST_EVENT\",\n" +
                        "  \"d\": {\n" +
                        "    \"application_id\": \"0\",\n" +
                        "    \"guild_id\": \"12345\",\n" +
                        "    \"id\": \"67890\",\n" +
                        "    \"name\": \"TEST NAME\",\n" +
                        "    \"op_user_id\": \"111111\",\n" +
                        "    \"owner_id\": \"2222222\",\n" +
                        "    \"parent_id\": \"33333333\",\n" +
                        "    \"permissions\": \"3\",\n" +
                        "    \"position\": 2,\n" +
                        "    \"private_type\": 0,\n" +
                        "    \"speak_permission\": 1,\n" +
                        "    \"sub_type\": 1,\n" +
                        "    \"type\": 0\n" +
                        "  }\n" +
                        "}");
    }
}
