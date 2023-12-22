package io.github.kloping.qqbot.http.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * {
 * "access_token": "ACCESS_TOKEN",
 * "expires_in": "7200"
 * }
 * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>access_token</td> <td>string</td> <td>获取到的凭证。</td></tr> <tr><td>expires_in</td> <td>number</td> <td>凭证有效时间，单位：秒。目前是7200秒之内的值。</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class Token {
    private String access_token;
    private String expires_in;

    /**
     * 用来记录获取时的时间戳
     */
    @JSONField(serialize = false, deserialize = false)
    private Long t0;

    public boolean isExpired() {
        Integer sec = Integer.valueOf(expires_in);
        Long t = t0 + (sec * 1000);
        return System.currentTimeMillis() >= t;
    }
}
