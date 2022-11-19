import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.qqpd.Guild;

import java.util.Arrays;

/**
 * @author github.kloping
 */
public class test_botGuilds {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        Guild[] guilds = starter.getBot().guilds().toArray(new Guild[0]);
        System.out.println(Arrays.toString(guilds));
    }
}
