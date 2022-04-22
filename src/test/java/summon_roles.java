import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.json.JsonSJC;

/**
 * @author github.kloping
 */
public class summon_roles {
    public static final JSONObject jo = JSON.parseObject("{\n" +
            "  \"guild_id\": \"123456\",\n" +
            "  \"roles\": [\n" +
            "    {\n" +
            "      \"id\": \"4\",\n" +
            "      \"name\": \"创建者\",\n" +
            "      \"color\": 4294927682,\n" +
            "      \"hoist\": 1,\n" +
            "      \"number\": 1,\n" +
            "      \"member_limit\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"2\",\n" +
            "      \"name\": \"管理员\",\n" +
            "      \"color\": 4280276644,\n" +
            "      \"hoist\": 1,\n" +
            "      \"number\": 5,\n" +
            "      \"member_limit\": 50\n" +
            "    }\n" +
            "  ],\n" +
            "  \"role_num_limit\": \"30\"\n" +
            "}\n");

    public static void main(String[] args) {
        JsonSJC.parse(jo, "./src/main/java/io/github/kloping/qqbot/api", "Roles0", "io.github.kloping.qqbot.api");
    }
}
