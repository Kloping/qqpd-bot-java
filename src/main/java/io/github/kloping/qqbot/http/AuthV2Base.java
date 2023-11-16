package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.http.Headers;
import io.github.kloping.MySpringTool.annotations.http.HttpClient;
import io.github.kloping.MySpringTool.annotations.http.PostPath;
import io.github.kloping.MySpringTool.annotations.http.RequestBody;
import io.github.kloping.qqbot.http.data.Token;

import java.util.Map;

/**
 * @author github.kloping
 */
@HttpClient("https://bots.qq.com/")
public interface AuthV2Base {
    /**
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>appId</td> <td>string</td> <td>是</td> <td>在开放平台管理端上获得。</td></tr> <tr><td>clientSecret</td> <td>string</td> <td>是</td> <td>在开放平台管理端上获得。</td></tr></tbody></table>
     *
     * @return
     */
    @PostPath("/app/getAppAccessToken")
    Token auth(@RequestBody String body, @Headers Map<String, String> headers);
}
