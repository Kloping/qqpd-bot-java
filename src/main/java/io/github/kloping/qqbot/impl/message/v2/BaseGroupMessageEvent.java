package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
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
import io.github.kloping.qqbot.http.data.V2Result;
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
     * <hr>
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>content</td> <td>string</td> <td>是</td> <td>文本内容</td></tr> <tr><td>msg_type</td> <td>int</td> <td>是</td> <td>消息类型： 0 是文本，1 图文混排 ，2 是 markdown 3 ark，4 embed</td></tr> <tr><td>markdown</td> <td>object</td> <td>否</td> <td>格式参考"消息类型=&gt;markdown=&gt;数据结构与协议"</td></tr> <tr><td>keyboard</td> <td>object</td> <td>否</td> <td>格式参考"消息交互=&gt;消息按钮=&gt;数据结构与协"</td></tr> <tr><td>ark</td> <td>object</td> <td>否</td> <td>格式参考"消息类型=&gt;ark=&gt;数据结构与协议"</td></tr> <tr><td>image</td> <td></td> <td>否</td> <td>【暂不支持】</td></tr> <tr><td>message_reference</td> <td>object</td> <td>否</td> <td>【暂未支持】消息引用</td></tr> <tr><td>event_id</td> <td>string</td> <td>否</td> <td>【暂未支持】前置收到的事件ID，用于发送被动消息</td></tr> <tr><td>msg_id</td> <td>string</td> <td>否</td> <td>前置收到的消息ID，用于发送被动消息</td></tr></tbody></table>
     *
     * @param text
     * @return
     */
    @Override
    public V2Result sendMessage(String text) {
        return bot.groupV2Base.send(getSubject().getOpenid(), String.format("{\"msg_type\":0,\"content\":\"%s\",\"msg_id\":\"%s\"}", text, getMsgId()), Channel.SEND_MESSAGE_HEADERS);
    }

    @Override
    public V2Result sendMessage(SendAble msg) {
        V2Result result = sendE0(msg);
        return result;
    }

    private V2Result sendE0(SendAble msg) {
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

    private V2Result sendChain(MessageChain chain) {
        V2Result result = new V2Result();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chain.size(); i++) {
            SendAble e = chain.get(i);
            if (e instanceof Image) {
                //仅能被动1次
                /*if (sb.length() > 0) {
                    result = sendMessage(sb.toString());
                    sb = new StringBuilder();
                }*/
                result = sendImage((Image) e);
            } else if (e instanceof MessageChain) {
                result = sendChain(chain);
            } else {
                sb.append(e.toString());
            }
        }
        if (sb.length() > 0) result = sendMessage(sb.toString());
        return result;
    }

    private V2Result sendImage(Image msg) {
        return bot.groupV2Base.sendFile(getSubject().getId()
                , String.format("{\"file_type\": 1,\"url\": \"%s\",\"srv_send_msg\": true}", msg.getUrl())
                , Channel.SEND_MESSAGE_HEADERS);
    }

    @Override
    public Group getGroup() {
        return getSubject();
    }
}
