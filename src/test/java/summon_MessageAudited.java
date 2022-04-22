import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.json.JsonSJC;

/**
 * @author github.kloping
 */
public class summon_MessageAudited {
    public static final JSONObject jo = JSON.parseObject("{\"code\":304023,\"message\":\"push message is waiting for audit now\",\"data\":{\"message_audit\":{\"audit_id\":\"611c4c21-84b8-4fe3-97c1-fcaff5457b4b\"}}}");

    public static void main(String[] args) {
        JsonSJC.parse(jo, "./src/main/java/io/github/kloping/qqbot/api", "MessageAudited", "io.github.kloping.qqbot.api");
    }
}
