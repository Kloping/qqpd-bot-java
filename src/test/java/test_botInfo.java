import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.User;
import io.github.kloping.qqbot.http.UserBase;

/**
 * @author github.kloping
 */
public class test_botInfo {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        UserBase base = starter.getContextManager().getContextEntity(UserBase.class);
        User user = base.botInfo();
        System.out.println(user);
    }
}
