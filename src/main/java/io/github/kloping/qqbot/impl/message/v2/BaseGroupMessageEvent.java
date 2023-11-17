package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.v2.GroupMessageEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.At;
import io.github.kloping.qqbot.entities.ex.Image;
import io.github.kloping.qqbot.entities.ex.PlainText;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import io.github.kloping.qqbot.entities.qqpd.v2.Member;
import io.github.kloping.qqbot.http.data.V2MsgData;
import lombok.Getter;

/**
 * @author github.kloping
 */
@Getter
public class BaseGroupMessageEvent extends BaseMessageEvent implements GroupMessageEvent {
    private Group subject;
    private Member sender;

    public BaseGroupMessageEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
        this.bot = bot;
        this.metadata = jo;
        this.rawMessage = message;
        this.subject = new Group(getMetadata());
        this.sender = new Member(getMetadata().getJSONObject("author"));
        this.msgId = getMetadata().getString("id");
    }

    @Override
    public String getGroupId() {
        return getSubject().getId();
    }

    /**
     * 发送纯文本
     * @param text
     * @return
     */
    @Override
    public String sendMessage(String text) {
        return sendMessage(text, 1);
    }

    public String sendMessage(String text, int seq) {
        V2MsgData data = new V2MsgData().setMsg_id(getMsgId()).setContent(text).setMsg_seq(1);
        return bot.groupV2Base.send(getSubject().getOpenid(), JSON.toJSONString(data), Channel.SEND_MESSAGE_HEADERS);
    }

    @Override
    public String sendMessage(SendAble msg) {
        String result = sendE0(msg);
        return result;
    }

    private String sendE0(SendAble msg) {
        if (msg instanceof PlainText) {
            return sendMessage(((PlainText) msg).getText());
        } else if (msg instanceof Image) {
            return sendImage((Image) msg);
        } else if (msg instanceof Emoji) {
            return sendMessage(msg.toString());
        } else if (msg instanceof At) {
            return sendMessage(msg.toString());
        } else if (msg instanceof MessageChain) {
            MessageChain chain = (MessageChain) msg;
            return sendChain(chain);
        }
        return null;
    }

    private String sendChain(MessageChain chain) {
        String result = null;
        StringBuilder sb = new StringBuilder();
        int seq = 1;
        for (int i = 0; i < chain.size(); i++) {
            SendAble e = chain.get(i);
            if (e instanceof Image) {
                //仅能被动1次
                if (sb.length() > 0) {
                    result = sendMessage(sb.toString(), seq++);
                    sb = new StringBuilder();
                }
                result = sendImage((Image) e);
            } else if (e instanceof MessageChain) {
                result = sendChain(chain);
            } else {
                sb.append(e.toString());
            }
        }
        if (sb.length() > 0) result = sendMessage(sb.toString(), seq);
        return result;
    }

    private String sendImage(Image msg) {
        if (Judge.isEmpty(msg.getUrl())) {
            if (msg.getBytes() != null) if (bot.getConfig().getInterceptor0() != null) {
                String url = bot.getConfig().getInterceptor0().upload(msg.getBytes());
                if (Judge.isNotEmpty(url)) {
                    msg.setUrl(url);
                } else return null;
            }
        }
        return bot.groupV2Base.sendFile(getSubject().getId(), String.format("{\"file_type\": %s,\"url\": \"%s\",\"srv_send_msg\": true}", msg.getFile_type(), msg.getUrl())
                , Channel.SEND_MESSAGE_HEADERS);
    }

    @Override
    public Group getGroup() {
        return getSubject();
    }
}
