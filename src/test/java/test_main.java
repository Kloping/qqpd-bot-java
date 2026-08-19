import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;
import io.github.kloping.qqbot.entities.qqpd.Dms;

/**
 * @author github.kloping
 */
public class test_main {

    public static void main(String[] args) {
        Starter starter = factory();
        //事件订阅 私域机器人
        // 推荐Intents.PRIVATE_INTENTS 公域机器人推荐 Intents.PUBLIC_INTENTS
        starter.getConfig().setCode(Intents.PUBLIC_INTENTS.and(Intents.GROUP_INTENTS));
        //重连
        starter.setReconnect(true);
        starter.run();
        // 设置日志等级 一般情况无需设置
    }

    public static Starter factory() {
        Starter starter = new Starter("appid", "secret");
        starter.getConfig().setCode(Intents.PUBLIC_INTENTS.and(Intents.GROUP_INTENTS));
        return starter;
    }
}
