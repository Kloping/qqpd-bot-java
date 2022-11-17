import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.Starter;

/**
 * <a href="https://bot.q.qq.com/wiki/develop/api/gateway/intents.html#%E4%BA%8B%E4%BB%B6%E8%AE%A2%E9%98%85-intents">
 * 事件订阅方式</a>
 *
 * @author github.kloping
 */
public class test_main {

    public static void main(String[] args) {
        System.out.println(0 | 1 << 30 | 1 << 9);
    }

    public static Starter factory() {
        Starter starter = new Starter("appid", "token") {
            @Override
            protected void after() {
                super.after();
                Resource.APPLICATION.INSTANCE.getContextManager().append("1073742336", INTENTS_ID);
            }

            @Override
            protected void wssWork() {
                super.wssWork();
            }
        };
        return starter;
    }
}
