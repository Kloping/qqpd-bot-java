package io.github.kloping.qqbot.http;

import io.github.kloping.qqbot.Start0;
import io.github.kloping.qqbot.http.data.Token;
import io.github.kloping.spt.annotations.http.Headers;
import io.github.kloping.spt.annotations.http.HttpClient;
import io.github.kloping.spt.annotations.http.PostPath;
import io.github.kloping.spt.annotations.http.RequestBody;

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
    Token auth(@RequestBody(type = io.github.kloping.spt.annotations.http.RequestBody.type.json) Start0.AppidAndSecret aa, @Headers Map<String, String> headers);
}
