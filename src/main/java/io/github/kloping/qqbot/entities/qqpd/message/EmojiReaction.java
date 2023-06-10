package io.github.kloping.qqbot.entities.qqpd.message;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.entities.qqpd.data.Emoji;
import lombok.Data;

/**
 * 表情表态对象
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>user_id</td> <td>string</td> <td>用户ID</td></tr> <tr><td>guild_id</td> <td>string</td> <td>频道ID</td></tr> <tr><td>channel_id</td> <td>string</td> <td>子频道ID</td></tr> <tr><td>target</td> <td>ReactionTarget</td> <td>表态对象</td></tr> <tr><td>emoji</td> <td><a href="/wiki/develop/api/openapi/emoji/model.html#Emoji" class="">Emoji</a></td> <td>表态所用表情</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
public class EmojiReaction {
    private String userId;
    private String guildId;
    private String channelId;
    private ReactionTarget target;
    @JSONField(serialize = false, deserialize = false)
    private Emoji emoji;

    /**
     * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>表态对象ID</td></tr> <tr><td>type</td> <td>ReactionTargetType</td> <td>表态对象类型，参考 ReactionTargetType</td></tr></tbody></table>
     */
    @Data
    public static class ReactionTarget {
        private String id;
        /**
         * <table><thead><tr><th>值</th> <th>描述</th></tr></thead> <tbody><tr><td>0</td> <td>消息</td></tr> <tr><td>1</td> <td>帖子</td></tr> <tr><td>2</td> <td>评论</td></tr> <tr><td>3</td> <td>回复</td></tr></tbody></table>
         */
        private Integer type;
    }
}
