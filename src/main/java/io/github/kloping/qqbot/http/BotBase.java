package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.http.GetPath;
import io.github.kloping.MySpringTool.annotations.http.Headers;
import io.github.kloping.MySpringTool.annotations.http.HttpClient;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.http.data.UrlPack;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_MAIN)
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
