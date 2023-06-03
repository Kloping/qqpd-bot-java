package gs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.common.Public;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.message.MessageChannelReceiveEvent;
import io.github.kloping.qqbot.api.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.api.message.MessageEvent;
import io.github.kloping.qqbot.entities.qqpd.message.DirectMessage;
import io.github.kloping.qqbot.entities.qqpd.message.Message;
import io.github.kloping.qqbot.impl.EventReceiver;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.kloping.qqbot.impl.MessagePacket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <a href="https://github.com/Genshin-bots/gsuid_core">gsuid_core</a>
 * 临时连接 <br>
 * 启动方式
 * GsClient.main(new String[]{"ws地址", "appid", "token",
 * "图床上传地址", "图床上传密码"});
 *
 * @author github.kloping
 */
public class GsClient extends WebSocketClient {
    public GsClient(URI serverUri) {
        super(serverUri);
    }

    public static final String SELF_ID = "pd-client";

    public static String up0;
    public static String pwd;

    public static Starter starter;
    public static GsClient gsClient;

    private static Map<String, Message> id2mes = new HashMap<>();

    public static void main(String[] args) throws Exception {
        up0 = args[3];
        pwd = args[4];
        URI uri = new URI(args[0] + SELF_ID);
        gsClient = new GsClient(uri);
        Public.EXECUTOR_SERVICE.submit(() -> gsClient.run());
        starter = new Starter(args[1], args[2]);
        starter.setReconnect(true);
        starter.run();
        starter.APPLICATION.logger.setLogLevel(0);
        starter.registerListenerHost(new ListenerHost() {
            @Override
            public void handleException(Throwable e) {

            }

            @EventReceiver
            public void onEvent(MessageChannelReceiveEvent event) {
                MessageReceive receive = getMr(event);
                if (receive != null) {
                    receive.setGroup_id(event.getChannelId());
                    receive.setUser_type("group");
                    gsClient.send(JSON.toJSONString(receive).getBytes());
                }
            }

            private MessageReceive getMr(MessageEvent event) {
                id2mes.put(event.getMessage().getId(), event.getMessage());
                String content = event.getMessage().getContent();
                if (content != null) {
                    MessageData message = new MessageData();
                    message.setType("text");
                    message.setData(content);
                    MessageReceive receive = new MessageReceive();
                    receive.setBot_id(SELF_ID);
                    receive.setBot_self_id(event.getBot().getId());
                    receive.setUser_id(event.getSender().getUser().getId());
                    receive.setMsg_id(event.getMessage().getId());
                    receive.setUser_pm(3);
                    receive.setContent(new MessageData[]{message});
                    return receive;
                }
                return null;
            }

            @EventReceiver
            public void onEvent(MessageDirectReceiveEvent event) {
                MessageReceive receive = getMr(event);
                if (receive != null) {
                    receive.setGroup_id("");
                    receive.setUser_type("direct");
                    gsClient.send(JSON.toJSONString(receive).getBytes());
                }
            }
        });
    }

    @Override
    public void send(byte[] data) {
        super.send(data);
        String json = new String(data, Charset.forName("utf-8"));
        starter.APPLICATION.logger.log("send=>" + json);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        starter.APPLICATION.logger.info("opened");
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
        String json = new String(bytes.array(), Charset.forName("utf-8"));
        starter.APPLICATION.logger.log("rec=>" + json);
        MessageOut out = JSONObject.parseObject(json, MessageOut.class);
        Message sender = id2mes.get(out.getMsg_id());
        if (out == null || sender == null || out.getBot_id() == null) return;
        MessagePacket packet = factory(sender, out);
        sender.send(packet);
    }

    public MessagePacket factory(Message sender, MessageOut out) {
        MessagePacket packet = new MessagePacket();
        packet.setReplyId(sender.getId());
        StringBuilder sb = new StringBuilder();
        if (!(sender instanceof DirectMessage)) sb.append("<@!" + sender.getAuthor().getId() + ">\n");
        for (MessageData data : out.getContent()) {
            if (data.getType().equals("node")) {
                try {
                    JSONArray array = (JSONArray) data.getData();
                    for (MessageData d0 : array.toJavaList(MessageData.class)) {
                        extracted(packet, sb, d0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            extracted(packet, sb, data);
        }
        packet.setContent(sb.toString());
        return packet;
    }

    private void extracted(MessagePacket packet, StringBuilder sb, MessageData d0) {
        if (d0.getType().equals("text")) {
            sb.append(d0.getData().toString()).append("\n");
        } else if (d0.getType().equals("image")) {
            packet.setImage(getImage(d0.getData().toString()));
        }
    }

    private String getImage(String base64) {
        byte[] bytes = Base64.getDecoder().decode(base64.substring("base64://".length()));
        try {
            String u1 = up0 + "/uploadImg?pwd=" + pwd;
            Document doc1 = Jsoup.connect(u1).ignoreHttpErrors(true).ignoreContentType(true).data("file", "temp.jpg", new ByteArrayInputStream(bytes)).timeout(60000).post();
            String url = doc1.body().text();
            System.out.println("上传成功↑(" + url + ")");
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void onMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        starter.APPLICATION.logger.error(String.format("close %s => %s", code, reason));
        Public.EXECUTOR_SERVICE.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                reconnect();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
