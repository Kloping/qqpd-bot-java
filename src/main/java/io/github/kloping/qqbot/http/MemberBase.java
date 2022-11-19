package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.http.Headers;
import io.github.kloping.MySpringTool.annotations.http.HttpClient;
import io.github.kloping.qqbot.Starter;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_MAIN)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface MemberBase {
}
