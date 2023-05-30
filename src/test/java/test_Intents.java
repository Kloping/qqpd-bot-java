import io.github.kloping.qqbot.Intents;
import io.github.kloping.qqbot.Starter;

/**
 * 事件订阅
 *
 * @author github.kloping
 */
public class test_Intents {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        Intents intents;
        //单事件订阅方式
        intents = Intents.START.and(Intents.GUILDS);
        //多事件订阅方式
        //Intents.START.and(Intents.GUILDS).and(Intents.GUILD_MEMBERS).and(Intents.GUILD_MESSAGES);

        // 公域机器人订阅推荐
        // starter.setIntents(Intents.PUBLIC_INTENTS);
        // 私域机器人订阅推荐
        // starter.setIntents(Intents.PRIVATE_INTENTS);

        starter.setIntents(intents);
        starter.run();
    }
}
