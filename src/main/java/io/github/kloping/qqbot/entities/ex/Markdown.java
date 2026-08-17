package io.github.kloping.qqbot.entities.ex;

import com.alibaba.fastjson.JSON;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
import io.github.kloping.qqbot.api.SenderV2;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.message.RawPreMessage;
import io.github.kloping.qqbot.http.data.Result;
import io.github.kloping.qqbot.http.data.V2MsgData;
import io.github.kloping.qqbot.http.data.V2Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static io.github.kloping.qqbot.entities.qqpd.Channel.SEND_MESSAGE_HEADERS;

/**
 * @author github.kloping
 */
@Getter
public class Markdown implements SendAble {
    /**
     * @deprecated Markdown 模板已被平台废弃，请改用 {@link #content}。
     */
    @Deprecated
    private String custom_template_id;
    private List<Param> params = null;

    /**
     * 原生md可用
     */
    private String content;

    private Keyboard keyboard;

    /**
     * @param custom_template_id 已废弃的自定义模板 ID
     * @deprecated Markdown 自定义模板已被平台废弃，请使用 {@link #Markdown()} 配合 {@link #setContent(String)}。
     */
    @Deprecated
    public Markdown(String custom_template_id) {
        this.custom_template_id = custom_template_id;
    }

    public Markdown() {
    }

    private static final Markdown EMPTY = new Markdown().setContent("> markdown is empty");

    public static Markdown ofEmpty() {
        return EMPTY;
    }

    public static Markdown ofText(String text) {
        return new Markdown().setContent(text);
    }

    public Markdown addParam(String key, String value) {
        if (params == null) params = new LinkedList<>();
        params.add(new Param(key, new String[]{value}));
        return this;
    }

    public Markdown setContent(String content) {
        this.content = content;
        return this;
    }

    public Markdown appendContent(String content) {
        this.content = this.content + content;
        return this;
    }

    public Markdown setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
        return this;
    }

    public Markdown setKeyboard(String id) {
        return setKeyboard(new Keyboard(id));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Param {
        public String key;
        private String[] values;
    }

    @Override
    public Result<V2Result> send(SenderAndCidMidGetter er) {
        return send(er, null);
    }

    public Result<V2Result> send(SenderAndCidMidGetter er, Integer msgSeq) {
        if (er.getEnvType().isV2()) {
            SenderV2 senderV2 = (SenderV2) er;
            if (msgSeq == null) msgSeq = senderV2.getMsgSeq();
            V2MsgData v2MsgData = new V2MsgData().setMarkdown(this).setMsg_type(2).setMsg_id(er.getMid()).setMsg_seq(msgSeq);
            if (keyboard != null) v2MsgData.setKeyboard(getKeyboard());
            return new Result(senderV2.getV2().send(er.getCid(), JSON.toJSONString(v2MsgData), SEND_MESSAGE_HEADERS));
        } else if (er.getEnvType() == EnvType.GUILD) {
            RawPreMessage preMessage = new RawPreMessage().setMarkdown(this).setMsgId(er.getMid());
            return new Result(er.getBot().messageBase.send(er.getCid(), preMessage, SEND_MESSAGE_HEADERS));
        }
        return er.send(this);
    }
}
