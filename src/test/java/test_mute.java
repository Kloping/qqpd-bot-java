import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.Guild;

/**
 * @author github.kloping
 */
public class test_mute {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        Bot bot = starter.getBot();
        Guild guild = null;
        guild.getMemberWithGuildId("xxx").mute(1);
    }
}
