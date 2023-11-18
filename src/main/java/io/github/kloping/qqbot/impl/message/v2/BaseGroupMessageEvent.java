package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.v2.GroupMessageEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Group;
import io.github.kloping.qqbot.entities.qqpd.v2.Member;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import lombok.Getter;
import lombok.Setter;

/**
 * @author github.kloping
 */
@Getter
public class BaseGroupMessageEvent extends BaseMessageEvent implements GroupMessageEvent, SenderAndCidMidGetter {
    @Getter
    private Group subject;
    private Member sender;
    @Setter
    private Bot bot;

    public BaseGroupMessageEvent(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
        this.bot = bot;
        this.metadata = jo;
        this.rawMessage = message;
        this.subject = new Group(getMetadata());
        this.subject.setBot(bot);
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
    public V2Result sendMessage(String text) {
        return sendMessage(text, 1);
    }

    public V2Result sendMessage(String text, int seq) {
        V2MsgData data = new V2MsgData().setMsg_id(getMsgId()).setContent(text).setMsg_seq(1);
        return bot.groupV2Base.send(getSubject().getOpenid(), JSON.toJSONString(data), Channel.SEND_MESSAGE_HEADERS);
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
        return getRawMessage().send(msg);
    }

    @Override
    public Group getGroup() {
        return getSubject();
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
        return EnvType.GROUP;
    }
}
