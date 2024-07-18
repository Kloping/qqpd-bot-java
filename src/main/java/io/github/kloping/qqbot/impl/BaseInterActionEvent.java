package io.github.kloping.qqbot.impl;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.api.event.InterActionEvent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.InterAction;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.http.data.Result;

/**
 * @author github.kloping
 */
public class BaseInterActionEvent implements InterActionEvent {

    public BaseInterActionEvent(Bot bot, JSONObject metaData) {
        interAction = metaData.toJavaObject(InterAction.class);
        interAction.setBot(bot);
    }

    private JSONObject metaData;
    private InterAction interAction;

    @Override
    public Result send(String text) {
        return getInterAction().send(text);
    }

    @Override
    public Result send(String text, RawMessage message) {
        return getInterAction().send(text, message);
    }

    @Override
    public Result send(SendAble msg) {
        return getInterAction().send(msg);
    }

    @Override
    public JSONObject getMetadata() {
        return metaData;
    }

    @Override
    public Bot getBot() {
        return getInterAction().getBot();
    }

    @Override
    public Integer getChatType() {
        return getInterAction().getChat_type();
    }

    @Override
    public InterAction getInterAction() {
        return interAction;
    }

    @Override
    public void response(int code) {
        getBot().interActionBase.response(getInterAction().getId(), String.format("{\"code\": %s}", code));
    }

    @Override
    public String toString() {
        return String.format("InterActionEvent [button %s(%s)] from %s"
                , getInterAction().getData().getResolved().getButton_data()
                , getInterAction().getData().getResolved().getButton_id()
                , getInterAction().getCid());
    }

    @Override
    public String getId() {
        return getMetadata().get("id").toString();
    }
}
