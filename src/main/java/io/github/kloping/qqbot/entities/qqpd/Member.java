package io.github.kloping.qqbot.entities.qqpd;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.AtAble;
import io.github.kloping.qqbot.api.OpAble;
import io.github.kloping.qqbot.api.SendAble;
import io.github.kloping.qqbot.entities.ex.At;
import io.github.kloping.qqbot.entities.ex.enums.EnvType;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Contact;
import io.github.kloping.qqbot.http.data.Result;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <table><thead><tr><th>字段名</th> <th>类型</th> <th>描述</th></tr></thead> <tbody><tr><td>user</td> <td><a href="/wiki/develop/api/openapi/user/model.html#user" class="">User</a></td> <td>用户的频道基础信息，只有成员相关接口中会填充此信息</td></tr> <tr><td>nick</td> <td>string</td> <td>用户的昵称</td></tr> <tr><td>roles</td> <td>string 数组</td> <td>用户在频道内的身份组ID, 默认值可参考<a href="/wiki/develop/api/openapi/guild/role_model.html#DefaultRoles" class="">DefaultRoles</a></td></tr> <tr><td>joined_at</td> <td>ISO8601 timestamp</td> <td>用户加入频道的时间</td></tr></tbody></table>
 *
 * @author github-kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode(callSuper = false)
public class Member extends Contact implements OpAble, AtAble {
    private String nick;
    private String joinedAt;
    private String[] roles;
    private User user;
    private String opUserId;

    @Override
    public At at() {
        return new At("member", user.getId());
    }

    @Getter
    @Setter
    @JSONField(serialize = false, deserialize = false)
    private Guild guild;

    @Override
    public String getId() {
        return user.getId();
    }

    @Override
    public String getOpenid() {
        return getId();
    }

    @Override
    public Result send(String text) {
        return getGuild().create(user.getId()).send(text);
    }

    @Override
    public Result send(String text, RawMessage message) {
        return getGuild().create(user.getId()).send(text, message);
    }

    @Override
    public Result send(SendAble msg) {
        return getGuild().create(getId()).send(msg);
    }

    @Override
    public String getCid() {
        return getGuild().create(getId()).getCid();
    }

    @Override
    public EnvType getEnvType() {
        return EnvType.GUILD;
    }
}