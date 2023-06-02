
import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entitys.qqpd.Member;

import java.util.Arrays;

/**
 * @author github.kloping
 */
public class test_botMembers {
    public static void main(String[] args) {
        Starter starter = test_main.factory();
        starter.run();
        Member[] ms = Resource.guildBase.getMembers(Resource.guildBase.getGuilds()[0].getId(), 100);
        System.out.println(Arrays.toString(ms));
    }
}
