import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Channel;
import io.github.kloping.qqbot.api.Guild;
import io.github.kloping.qqbot.http.GuildBase;
import io.github.kloping.qqbot.http.MessageBase;

/**
 * @author github.kloping
 */
public class test_sendMessage {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        Thread.sleep(2000);
        GuildBase base0 = starter.getContextManager().getContextEntity(GuildBase.class);
        MessageBase base = starter.getContextManager().getContextEntity(MessageBase.class);
        Guild[] guilds = base0.getGuilds();
        Channel[] channels = base0.getChannels(guilds[0].getId());
        Channel channel = null;
        for (Channel channel1 : channels) {
            if (channel1.getName().equals("游戏大厅")) {
                channel = channel1;
            }
        }
        System.out.println(channel.send("1"));
    }
}
