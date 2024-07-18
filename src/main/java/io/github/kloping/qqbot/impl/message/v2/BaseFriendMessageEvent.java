package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.api.v2.FriendMessageEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Friend;
import io.github.kloping.qqbot.http.BaseV2;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import io.github.kloping.qqbot.network.Events;
import lombok.Setter;

/**
 * @author github.kloping
 */
public class BaseFriendMessageEvent extends BaseMessageEvent<Friend> implements FriendMessageEvent, SenderAndCidMidGetter, SenderV2 {
    private Friend subject;
    @Setter
    private Bot bot;

    private Integer seq = 1;

    public BaseFriendMessageEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
        this.bot = bot;
        this.metadata = jo;
        this.rawMessage = message;

        this.msgId = getMetadata().getString("id");
        this.subject = new Friend(getMetadata().getJSONObject("author"));

        this.subject.setBot(bot);
    }

    @Override
    public String getId() {
        return metadata.getString(Events.EXTEND_ID);
    }

    @Override
    public Friend getFriend() {
        return subject;
    }

    @Override
    public String getOpenId() {
        return getFriend().getOpenid();
    }

    @Override
    public Friend getSender() {
        return getFriend();
    }

    @Override
    public Friend getSubject() {
        return subject;
    }

    /**
     * 发送纯文本
     *
     * @param text
     * @return
     */
    @Override
    public V2Result sendMessage(String text) {
        return sendMessage(text, getMsgSeq());
    }

    public V2Result sendMessage(String text, int seq) {
        V2MsgData data = new V2MsgData().setMsg_id(getMsgId()).setContent(text).setMsg_seq(seq);
        return getV2().send(getSubject().getOpenid(), JSON.toJSONString(data), Channel.SEND_MESSAGE_HEADERS);
    }

    @Override
    public V2Result sendMessage(SendAble msg) {
        return (V2Result) msg.send(this).getData();
    }

    @Override
    public Result<V2Result> send(String text) {
        return new Result<>(sendMessage(text));
    }

    @Override
    public Result<V2Result> send(String text, RawMessage message) {
        return message.send(text, message);
    }

    @Override
    public Result send(SendAble msg) {
        return msg.send(this);
    }

    @Override
    public String getCid() {
        return getSubject().getCid();
    }

    @Override
    public String getMid() {
        return getMsgId();
    }

    @Override
    public EnvType getEnvType() {
        return EnvType.GROUP_USER;
    }

    @Override
    public BaseV2 getV2() {
        return bot.userBaseV2;
    }

    public Integer getMsgSeq() {
        return seq++;
    }

    @Override
    public Integer setMsgSeq(Integer seq) {
        Integer oseq = this.seq;
        this.seq = seq;
        return oseq;
    }

    @Override
    public String toString() {
        return String.format("[type(%s) %s].%s:%s"
                , EnvType.USER.name()
                , getSubject().getId()
                , getSender().getId()
                , getRawMessage().toString0()
        );
    }

    @Override
    public String getClassName() {
        return "FriendMessageEvent";
    }
}
