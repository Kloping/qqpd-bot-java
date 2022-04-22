import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.json.JsonSJC;

/**
 * @author github.kloping
 */
public class summon_member {
    public static final JSONObject jo = JSON.parseObject("{\n" +
            "    \"user\": {\n" +
            "      \"id\": \"xxxxxx\",\n" +
            "      \"username\": \"xxxx\",\n" +
            "      \"avatar\": \"xxxxxx\",\n" +
            "      \"bot\": false,\n" +
            "      \"public_flags\": 0,\n" +
            "      \"system\": false,\n" +
            "      \"union_openid\": \"xxxxxx\",\n" +
            "      \"union_user_account\": \"\"\n" +
            "    },\n" +
            "    \"nick\": \"\",\n" +
            "    \"roles\": [\"1\"],\n" +
            "    \"joined_at\": \"2021-12-09T15:53:41+08:00\",\n" +
            "    \"deaf\": false,\n" +
            "    \"mute\": false,\n" +
            "    \"pending\": false\n" +
            "  }");

    public static void main(String[] args) {
        JsonSJC.parse(jo, "./src/main/java/io/github/kloping/qqbot/api", "Member", "io.github.kloping.qqbot.api");
    }
}
