import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.http.data.UrlPack;

/**
 * @author github.kloping
 */
public class test_gateway {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        UrlPack pack = Resource.botBase.gateway();
        System.out.println(pack);
    }
}
