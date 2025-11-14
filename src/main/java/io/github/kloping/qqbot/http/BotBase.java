package io.github.kloping.qqbot.http;

import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.http.data.UrlPack;
import io.github.kloping.spt.annotations.http.GetPath;
import io.github.kloping.spt.annotations.http.Headers;
import io.github.kloping.spt.annotations.http.HttpClient;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface BotBase {
    /**
     * 获取网关
     *
     * @return
     */
    @GetPath("/gateway")
    UrlPack gateway();

    /**
     * 获取网关 gateway/bot
     *
     * @return
     */
    @GetPath("/gateway/bot")
    UrlPack gateway0();
}
