
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Member;
import io.github.kloping.qqbot.http.GuildBase;

import java.util.Arrays;

/**
 * @author github.kloping
 */
public class test_botMembers {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        GuildBase base = starter.getContextManager().getContextEntity(GuildBase.class);
        Member[] ms = base.getMembers(base.getGuilds()[0].getId(),100);
        System.out.println(Arrays.toString(ms));
    }
}
