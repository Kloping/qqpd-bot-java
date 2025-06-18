package io.github.kloping.qqbot.http;

import io.github.kloping.spt.annotations.http.*;
import io.github.kloping.qqbot.Starter;
import org.jsoup.Connection;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface InterActionBase {
    /**
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>interaction_id</td> <td>string</td> <td>是</td> <td>上述事件中获得。</td></tr></tbody></table>
     * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>code</td> <td>int</td> <td>是</td> <td>0 成功<br>1 操作失败<br>2 操作频繁<br>3 重复操作<br>4 没有权限<br>5 仅管理员操作</td></tr></tbody></table>
     *  @param interaction_id
     *
     * @param body
     */
    @RequestPath(value = "/interactions/{interaction_id}", method = Connection.Method.PUT)
    void response(@PathValue("interaction_id") String interaction_id, @RequestBody String body);
}
