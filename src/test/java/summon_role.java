import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.json.JsonSJC;

/**
 * @author github.kloping
 */
public class summon_role {
    public static final JSONObject jo = JSON.parseObject(" {\n" +
            "      \"id\": \"4\",\n" +
            "      \"name\": \"创建者\",\n" +
            "      \"color\": 4294927682,\n" +
            "      \"hoist\": 1,\n" +
            "      \"number\": 1,\n" +
            "      \"member_limit\": 1\n" +
            "    }");

    public static void main(String[] args) {
        JsonSJC.parse(jo, "./src/main/java/io/github/kloping/qqbot/api", "Role", "io.github.kloping.qqbot.api");
    }
}
