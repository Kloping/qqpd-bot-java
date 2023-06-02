import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.qqpd.User;

/**
 * @author github.kloping
 */
public class test_botInfo {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        User user = Resource.userBase.botInfo();
        System.out.println(user);
    }
}
