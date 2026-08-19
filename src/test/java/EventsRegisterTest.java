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
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author github.kloping
 */
@Slf4j
public class EventsRegisterTest extends ListenerHost {
    private Starter starter;
    @Test
    public void testBefore() throws Throwable {
//        String appid = System.getProperty("appid");
//        String secret = System.getProperty("secret");
//        starter = new Starter(appid, secret);
//        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
//        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
//        starter.setReconnect(true);
//        starter.registerListenerHost(this);
//        test0();
//        starter.run();
//        logger = starter.APPLICATION.logger;
//        TimeUnit.SECONDS.sleep(8);
        log.info("测试通过.");
    }

    @Entity
    @Slf4j
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
                log.info("=====================================");
                log.warn("{} handle for raw {}", TEST_EVENT, mateData);
            }
            return null;
        }
    }

    private void test0() {
        starter.registerEventsRegister(TestEventsRegister.class);
    }

    @EventReceiver
    public void online(ConnectedEvent event) {
        log.info("bot online start test");
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
