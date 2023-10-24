import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.ChannelData;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.Guild;

/**
 * @author github.kloping
 */
public class test_channel_create_delete {

    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        Bot bot = starter.getBot();
        for (Guild guild : bot.guilds()) {
            for (Channel channel : guild.channels()) {
                if (channel.getName().equals("xxx")) {
                    //频道删除
                    channel.delete();
                }
            }
            //频道创建 创建详细信息  见 ChannelData 文档
            guild.create(new ChannelData()
                    .name("测试创建子频道")
            );
        }
    }
}
