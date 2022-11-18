package io.github.kloping.qqbot.http;

import io.github.kloping.MySpringTool.annotations.PathValue;
import io.github.kloping.MySpringTool.annotations.http.GetPath;
import io.github.kloping.MySpringTool.annotations.http.Headers;
import io.github.kloping.MySpringTool.annotations.http.HttpClient;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Channel;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_MAIN)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface ChannelBase {
    /**
     * 获取子频道详情
     *
     * @param cid
     * @return
     */
    @GetPath("/channels/{channels_id}")
    Channel getChannel(@PathValue("channels_id") String cid);
}
