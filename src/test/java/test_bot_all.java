import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Guild;
import io.github.kloping.qqbot.entities.qqpd.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author github.kloping
 */
public class test_bot_all {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        Bot bot = starter.getBot();
        //获取 bot 下所有 频道
        List<Guild> guilds = new ArrayList<>(bot.guilds());
        Guild guild = null;
        //获得 子频道下 所有 子频道
        guild.channels();
        //获得bot信息
        User user = bot.getInfo();

    }
}
