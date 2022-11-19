import io.github.kloping.qqbot.Starter;

/**
 * <a href="https://bot.q.qq.com/wiki/develop/api/gateway/intents.html#%E4%BA%8B%E4%BB%B6%E8%AE%A2%E9%98%85-intents">
 * 事件订阅方式</a>
 *
 * @author github.kloping
 */
public class test_main {

    //0 1 9 10 12 18 19 26 27 28 29 30
    public static void main(String[] args) {
        System.out.println(0 | 1 << 0 | 1 << 30
                | 1 << 1 | 1 << 9 | 1 << 10 | 1 << 29
                | 1 << 12 | 1 << 18 | 1 << 19
                | 1 << 28 | 1 << 27 | 1 << 26
        );
    }

    public static Starter factory() {
        Starter starter = new Starter("appid", "token");
        return starter;
    }
}
