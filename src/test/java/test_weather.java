
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Channel;
import io.github.kloping.qqbot.api.Guild;
import io.github.kloping.qqbot.api.message.Message;
import io.github.kloping.qqbot.http.GuildBase;
import io.github.kloping.qqbot.http.MessageBase;
import io.github.kloping.qqbot.interfaces.OnMessageListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author github.kloping
 */
public class test_weather {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        GuildBase base0 = starter.getContextManager().getContextEntity(GuildBase.class);
        MessageBase base = starter.getContextManager().getContextEntity(MessageBase.class);
        Guild[] guilds = base0.getGuilds();
        Channel[] channels = base0.getChannels(guilds[0].getId());
        Channel channel = null;
        for (Channel channel1 : channels) {
            if (channel1.getName().equals("游戏大厅"))
                channel = channel1;
        }
        String w0 = "天气";
        Channel finalChannel = channel;
        finalChannel.send("启动消息测试");
        starter.addMessageListener(new OnMessageListener() {
            @Override
            public void onMessage(Message message) {
                String content = message.getContent();
                if (content == null && content.isEmpty()) {
                    return;
                } else if (content.startsWith(w0)) {
                    String s = content.substring(w0.length());
                    finalChannel.send("<@" + message.getAuthor().getId() + ">\n" + s + "天气:" + w0(s));
                }
            }
        });
    }

    public static String w0(String s0) {
        Document document = null;
        try {
            document = Jsoup.connect("http://kloping.top/api/get/weather?address=" + s0).ignoreContentType(true)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = document.body().text();
        JSONObject jo = JSON.parseObject(json);
        return jo.getString("described");
    }
}
