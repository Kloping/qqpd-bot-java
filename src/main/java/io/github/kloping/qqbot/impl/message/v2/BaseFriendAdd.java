package io.github.kloping.qqbot.impl.message.v2;

import com.alibaba.fastjson.JSONObject;
import io.github.kloping.qqbot.api.v2.FriendAdd;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;

/**
 * @author github.kloping
 */
public class BaseFriendAdd extends BaseFriendEvent implements FriendAdd {
    public BaseFriendAdd(RawMessage message, JSONObject jo, Bot bot) {
        super(message, jo, bot);
    }

    @Override
    public String toString() {
        return "FRIEND_ADD EVENT FID:" + getOpenId();
    }

    @Override
    public String getClassName() {
        return "FriendAdd";
    }
}
