package io.github.kloping.qqbot.http;

import io.github.kloping.qqbot.http.data.V2Result;
import io.github.kloping.spt.annotations.http.Headers;
import io.github.kloping.spt.annotations.http.RequestBody;

import java.util.Map;

/**
 * base v2
 */
public interface BaseV2 {
    V2Result send(String gid, @RequestBody(type = io.github.kloping.spt.annotations.http.RequestBody.type.json) String body, @Headers Map<String, String> headers);

    V2Result sendFile(String gid, @RequestBody(type = io.github.kloping.spt.annotations.http.RequestBody.type.json) String body, @Headers Map<String, String> headers);
}
