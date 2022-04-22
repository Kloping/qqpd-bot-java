import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entitys.Pack;
import io.github.kloping.qqbot.entitys.UrlPack;
import io.github.kloping.qqbot.http.BotBase;
import io.github.kloping.qqbot.interfaces.OnPackReceive;

/**
 * @author github.kloping
 */
public class test_gateway {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        starter.setOnPackReceive(new OnPackReceive() {
            @Override
            public void onReceive(Pack pack) {
            }
        });
        UrlPack pack = starter.getContextManager().getContextEntity(BotBase.class).gateway();
        System.out.println(pack);
    }
}
