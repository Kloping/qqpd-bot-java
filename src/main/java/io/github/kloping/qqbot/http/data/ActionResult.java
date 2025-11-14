package io.github.kloping.qqbot.http.data;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.message.audited.MessageAudited;
import lombok.Getter;
import lombok.ToString;

/**
 * @author github.kloping
 */
@Getter
@ToString
public class ActionResult {
    public static ActionResult doc(String json) {
        try {
            RawMessage message = JSONObject.parseObject(json, RawMessage.class);
            if (message.getId() != null) return new ActionResult(message, null);
             else return new ActionResult(null, JSONObject.parseObject(json, MessageAudited.class));
        } catch (Exception e) {
            return null;
        }
    }

    private Boolean sent;
    private RawMessage rawMessage;
    private MessageAudited messageAudited;

    public ActionResult(RawMessage rawMessage, MessageAudited messageAudited) {
        this.rawMessage = rawMessage;
        this.messageAudited = messageAudited;
        sent = rawMessage == null ? false : true;
    }
}
