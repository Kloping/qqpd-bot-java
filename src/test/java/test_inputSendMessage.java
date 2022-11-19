import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.qqpd.Channel;
import io.github.kloping.qqbot.api.qqpd.Guild;

import java.util.Scanner;

/**
 * @author github.kloping
 */
public class test_inputSendMessage {
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
        String line = null;
        Scanner sc = new Scanner(System.in);
        while (true) {
            line = sc.nextLine();
            if (line == null || line.isEmpty()) continue;
            channel.send(line);
        }
    }
}
