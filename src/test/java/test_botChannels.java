import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Channel;
import io.github.kloping.qqbot.api.Guild;
import io.github.kloping.qqbot.http.GuildBase;

import java.util.Arrays;

/**
 * @author github.kloping
 */
public class test_botChannels {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        Guild[] guilds = starter.getBot().guilds().toArray(new Guild[0]);
        Channel[] channels = Resource.guildBase.getChannels(guilds[0].getId());
        System.out.println(Arrays.toString(channels));
    }
}
