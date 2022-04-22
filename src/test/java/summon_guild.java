import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.json.JsonSJC;

/**
 * @author github.kloping
 */
public class summon_guild {
    public static final JSONObject jo = JSON.parseObject("{\n" +
            "  \"id\": \"123456\",\n" +
            "  \"name\": \"xxxxxx\",\n" +
            "  \"icon\": \"xxxxxx\",\n" +
            "  \"owner_id\": \"xxxxxx\",\n" +
            "  \"owner\": false,\n" +
            "  \"joined_at\": \"2022-01-13T11:02:21+08:00\",\n" +
            "  \"member_count\": 5,\n" +
            "  \"max_members\": 300,\n" +
            "  \"description\": \"千江有水千江月，万里无云万里天\"\n" +
            "}\n");

    public static void main(String[] args) {
        JsonSJC.parse(jo, "./src/main/java/io/github/kloping/qqbot/api", "Guild", "io.github.kloping.qqbot.api");
    }
}
