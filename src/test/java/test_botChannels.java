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
        GuildBase base = starter.getContextManager().getContextEntity(GuildBase.class);
        Guild[] guilds = base.getGuilds();
        Channel[] channels = base.getChannels(guilds[0].getId());
        System.out.println(Arrays.toString(channels));
    }
}
