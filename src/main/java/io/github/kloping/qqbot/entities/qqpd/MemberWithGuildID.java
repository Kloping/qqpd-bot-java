package io.github.kloping.qqbot.entities.qqpd;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.event.BotContent;
import io.github.kloping.qqbot.entities.Bot;
import io.github.kloping.qqbot.http.data.MutePack;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead>
 * <tbody><tr><td>guild_id</td> <td>string</td> <td>频道id</td></tr> <tr><td>user</td>
 * <td>{@link User}</a></td> <td>用户的频道基础信息</td></tr> <tr><td>nick</td> <td>string</td> <td>用户的昵称</td></tr> <tr><td>roles</td> <td>string 数组</td> <td>用户在频道内的身份</td></tr> <tr><td>joined_at</td> <td>ISO8601 timestamp</td> <td>用户加入频道的时间</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
public class MemberWithGuildID extends Member implements BotContent {
    private String guildId;

    /**
     * 禁言秒数
     *
     * @param seconds
     */
    public void mute(int seconds) {
        MutePack mutePack = new MutePack();
        mutePack.setMuteSeconds(seconds);
        bot.memberBase.muteOne(guildId, getUser().getId(), mutePack);
    }

    /**
     * 禁言到指定时间戳 单位:秒
     *
     * @param timestamp
     */
    public void mute(long timestamp) {
        MutePack mutePack = new MutePack();
        mutePack.setMuteEndTimestamp(timestamp);
        bot.memberBase.muteOne(guildId, getUser().getId(), mutePack);
    }

    @JSONField(serialize = false, deserialize = false)
    private Bot bot;

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}