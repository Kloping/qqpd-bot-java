package io.github.kloping.qqbot.http;

import io.github.kloping.qqbot.entities.qqpd.User;
import io.github.kloping.spt.annotations.http.GetPath;
import io.github.kloping.spt.annotations.http.Headers;
import io.github.kloping.spt.annotations.http.HttpClient;

import static io.github.kloping.qqbot.Starter.NET_POINT;

/**
 * @author github.kloping
 */
@HttpClient(NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface UserBase {
    /**
     * bot info
     *
     * @return
     */
    @GetPath("/users/@me")
    User botInfo();
}
