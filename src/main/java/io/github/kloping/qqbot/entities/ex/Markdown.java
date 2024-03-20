package io.github.kloping.qqbot.entities.ex;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.SenderAndCidMidGetter;
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
    private String custom_template_id;
    private List<Param> params = null;

    /**
     * 原生md可用
     */
    private String content;

    private Keyboard keyboard;

    /**
     * 需要再<a href="https://q.qq.com/qqbot/#/developer/advanced-features">QQ机器人管理平台</a> 申请并审核通过后使用
     *
     * @param custom_template_id
     */
    public Markdown(String custom_template_id) {
        this.custom_template_id = custom_template_id;
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
        return send(er, 1);
    }

    public Result<V2Result> send(SenderAndCidMidGetter er, Integer msgSeq) {
        if (er.getEnvType() == EnvType.GROUP) {
            V2MsgData v2MsgData = new V2MsgData();
            v2MsgData.setMarkdown(this);
            v2MsgData.setMsg_type(2);
            v2MsgData.setMsg_id(er.getMid());
            if (keyboard != null) v2MsgData.setKeyboard(getKeyboard());
            return new Result(er.getBot().groupBaseV2.send(er.getCid(), JSON.toJSONString(v2MsgData), SEND_MESSAGE_HEADERS));
        } else if (er.getEnvType() == EnvType.GUILD) {
            RawPreMessage preMessage = new RawPreMessage();
            preMessage.setMarkdown(this);
            preMessage.setMsgId(er.getMid());
            return new Result(er.getBot().messageBase.send(er.getCid(), preMessage, SEND_MESSAGE_HEADERS));
        }
        return er.send(this);
    }
}
