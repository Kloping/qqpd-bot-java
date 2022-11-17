
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Channel;
import io.github.kloping.qqbot.api.Guild;
import io.github.kloping.qqbot.api.message.Message;
import io.github.kloping.qqbot.http.GuildBase;
import io.github.kloping.qqbot.http.MessageBase;
import io.github.kloping.qqbot.interfaces.OnAtMessageListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author github.kloping
 */
public class test_xiaoai {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        String w0 = String.format("<@!%s> /小爱", starter.getBot().getInfo().getId());
        starter.addListener(new OnAtMessageListener() {
            @Override
            public void onMessage(Message message) {
                String content = message.getContent();
                if (content == null && content.isEmpty()) {
                    return;
                } else if (content.startsWith(w0)) {
                    String s = content.substring(w0.length());
                    String r0 = w0(s.trim());
                    message.send("<@!" + message.getAuthor().getId() + ">  \n" + r0);
                }
            }
        });
    }

    public static String w0(String s0) {
        try {
            Document document = null;
            try {
                document = Jsoup.connect("http://api.weijieyue.cn/api/xiaoai/api.php?msg=" + s0).ignoreContentType(true)
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String json = document.body().text();
            JSONObject jo = JSON.parseObject(json);
            return jo.getString("text");
        } catch (Exception e) {
            e.printStackTrace();
            return "???";
        }
    }
}
