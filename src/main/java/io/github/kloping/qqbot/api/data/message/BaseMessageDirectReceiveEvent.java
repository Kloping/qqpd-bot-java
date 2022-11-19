package io.github.kloping.qqbot.api.data.message;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.Resource;
import io.github.kloping.qqbot.api.data.MessagePacket;
import io.github.kloping.qqbot.api.interfaces.message.MessageDirectReceiveEvent;
import io.github.kloping.qqbot.api.qqpd.message.Message;
import io.github.kloping.qqbot.api.qqpd.message.PreMessage;
import io.github.kloping.qqbot.api.qqpd.message.audited.MessageAudited;

/**
 * @author github.kloping
 */
public class BaseMessageDirectReceiveEvent extends BaseMessageReceiveEvent implements MessageDirectReceiveEvent {
    public BaseMessageDirectReceiveEvent(Message message, JSONObject jo) {
        super();
        this.message = message;
        this.metadata = jo;
        this.guild = Resource.starter.getBot().getGuild(message.getGuildId());
        this.sender = message.getMember();
        this.srcGuildId = getMessage().getSrcGuildId();
    }

    private String srcGuildId;

    @Override
    public String getSrcGuildId() {
        return srcGuildId;
    }
    /**
     * 替换默认
     *
     * @param text
     * @return
     */
    @Override
    public MessageAudited send(String text) {
        return sendDirect(text);
    }

    /**
     * 替换默认
     *
     * @param text
     * @param message
     * @return
     */
    @Override
    public MessageAudited send(String text, Message message) {
        return sendDirect(text, message);
    }

    /**
     * 替换默认
     *
     * @param packet
     * @return
     */
    @Override
    public MessageAudited send(MessagePacket packet) {
        return sendDirect(packet);
    }

    /**
     * 替换默认
     *
     * @param msg
     * @return
     */
    @Override
    public MessageAudited send(PreMessage msg) {
        return sendDirect(msg);
    }
}