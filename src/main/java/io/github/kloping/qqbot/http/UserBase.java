package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.http.GetPath;
import io.github.kloping.MySpringTool.annotations.http.Headers;
import io.github.kloping.MySpringTool.annotations.http.HttpClient;
import io.github.kloping.qqbot.entitys.qqpd.User;

import static io.github.kloping.qqbot.Starter.NET_MAIN;

/**
 * @author github.kloping
 */
@HttpClient(NET_MAIN)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface UserBase {
    /**
     * bot info
     *
     * @param headers
     * @return
     */
    @GetPath("/users/@me")
    User botInfo();
}
