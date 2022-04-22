import io.github.kloping.qqbot.Starter;

/**
 * @author github.kloping
 */
public class test_main {
    public static Starter factory() {
        Starter starter = new Starter("appid", "token") {
            @Override
            protected void after() {
                super.after();
            }

            @Override
            protected void wssWork() {
                super.wssWork();
            }
        };
        return starter;
    }
}
