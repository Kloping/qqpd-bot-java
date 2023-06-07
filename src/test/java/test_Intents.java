import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;

/**
 * 事件订阅
 *
 * @author github.kloping
 */
public class test_Intents {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        //单事件订阅方式
        starter.getConfig().setCode(Intents.GUILD_MESSAGES.getCode());

        //多事件订阅方式
        starter.getConfig().setCode(Intents.START.and(Intents.GUILD_MESSAGES, Intents.DIRECT_MESSAGE));

        // 公域机器人订阅推荐
        starter.getConfig().setCode(Intents.PUBLIC_INTENTS.getCode());
        // 私域机器人订阅推荐
        starter.getConfig().setCode(Intents.PRIVATE_INTENTS.getCode());
        starter.run();
    }
}
