
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.json.JsonSJC;

/**
 * @author github.kloping
 */
public class summon_channel {
    public static final JSONObject jo = JSON.parseObject("{\n" +
            "  \"id\": \"123456\",\n" +
            "  \"guild_id\": \"xxxxxx\",\n" +
            "  \"name\": \"很高兴遇见你\",\n" +
            "  \"type\": 4,\n" +
            "  \"position\": 2,\n" +
            "  \"owner_id\": \"0\",\n" +
            "  \"sub_type\": 0,\n" +
            "  \"private_type\": 0,\n" +
            "  \"speak_permission\": 0,\n" +
            "  \"application_id\": \"0\"\n" +
            "}\n");

    public static void main(String[] args) {
        JsonSJC.parse(jo, "./src/main/java/io/github/kloping/qqbot/api", "Channel", "io.github.kloping.qqbot.api");
    }
}
