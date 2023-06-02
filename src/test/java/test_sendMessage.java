import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.Guild;

/**
 * @author github.kloping
 */
public class test_sendMessage {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        Guild[] guilds = starter.getBot().guilds().toArray(new Guild[0]);
        Channel[] channels = Resource.guildBase.getChannels(guilds[0].getId());
        Channel channel = null;
        for (Channel channel1 : channels) {
            if (channel1.getName().equals("游戏大厅")) {
                channel = channel1;
            }
        }
        Thread.sleep(5000);
        channel.send("测试消息");
    }
}
