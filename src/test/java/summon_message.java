import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.json.JsonSJC;

/**
 * @author github.kloping
 */
public class summon_message {
    public static final JSONObject jo = JSON.parseObject("{\n" +
            "  \"id\": \"112233\",\n" +
            "  \"channel_id\": \"123456\",\n" +
            "  \"guild_id\": \"xxxxxx\",\n" +
            "  \"content\": \"hello\",\n" +
            "  \"timestamp\": \"2021-05-25T15:20:34+08:00\",\n" +
            "  \"author\": {\n" +
            "    \"id\": \"xxxxxx\",\n" +
            "    \"username\": \"az\",\n" +
            "    \"bot\": false\n" +
            "  },\n" +
            "  \"member\": {\n" +
            "    \"roles\": [\"1\"],\n" +
            "    \"joined_at\": \"2021-04-12T16:34:42+08:00\"\n" +
            "  }\n" +
            "}\n");

    public static void main(String[] args) {
        JsonSJC.parse(jo, "./src/main/java/io/github/kloping/qqbot/api", "Message", "io.github.kloping.qqbot.api");
    }
}
