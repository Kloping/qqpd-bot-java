import io.github.kloping.MySpringTool.StarterApplication;
import io.github.kloping.qqbot.Starter;

/**
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
                StarterApplication.Setting.INSTANCE.getContextManager().append("1073742336", INTENTS_ID);
            }

            @Override
            protected void wssWork() {
                super.wssWork();
            }
        };
        return starter;
    }
}
