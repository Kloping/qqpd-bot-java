package io.github.kloping.qqbot.entities.qqpd;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://bot.q.qq.com/wiki/develop/api/openapi/pins/model.html#pinsmessage">source</a>
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>guild_id</td> <td>string</td> <td>频道 id</td></tr> <tr><td>channel_id</td> <td>string</td> <td>子频道 id</td></tr> <tr><td>message_ids</td> <td>string 数组</td> <td>子频道内精华消息 id 数组</td></tr></tbody></table>
 *
 * @author github.kloping
 */
public class PinsMessage {
    private String guild_id;
    private String channel_id;
    private List<String> message_ids = new ArrayList<>();

    public String getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(String guild_id) {
        this.guild_id = guild_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public List<String> getMessage_ids() {
        return message_ids;
    }

    public void setMessage_ids(List<String> message_ids) {
        this.message_ids = message_ids;
    }
}
