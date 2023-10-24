import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;

/**
 * @author github.kloping
 */
public class test_main {

    public static void main(String[] args) {
        Starter starter = factory();
        //事件订阅 私域机器人
        // 推荐Intents.PRIVATE_INTENTS 公域机器人推荐 Intents.PUBLIC_INTENTS
        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
        //重连
        starter.setReconnect(true);
        starter.run();
        // 设置日志等级 一般情况无需设置
        // starter.APPLICATION.logger.setLogLevel(0);
    }

    public static Starter factory() {
        Starter starter = new Starter("appid", "token");
        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
        return starter;
    }
}
