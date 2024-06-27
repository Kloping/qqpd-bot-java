package io.github.kloping.qqbot.http;

import io.github.kloping.spt.annotations.http.*;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.qqpd.Channel;
import io.github.kloping.qqbot.entities.qqpd.PinsMessage;
import io.github.kloping.qqbot.entities.qqpd.message.MessagePack;
import org.jsoup.Connection;

/**
 * @author github.kloping
 */
@HttpClient(Starter.NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface ChannelBase {
    /**
     * 获取子频道详情
     *
     * @param cid
     * @return
     */
    @GetPath("/channels/{channel_id}")
    Channel getChannel(@PathValue("channel_id") String cid);

    /**
     * 获取指定消息
     *
     * @param cid
     * @param mid
     * @return
     */
    @GetPath("/channels/{channel_id}/messages/{message_id}")
    MessagePack getMessageById(@PathValue("channel_id") String cid, @PathValue("message_id") String mid);

    /**
     * 添加一个emoji
     *
     * @param cid
     * @param mid
     * @param type
     * @param id
     * @return
     */
    @RequestPath(method = Connection.Method.PUT, value = "/channels/{channel_id}/messages/{message_id}/reactions/{type}/{id}")
    void addEmoji(@PathValue("channel_id") String cid, @PathValue("message_id") String mid, @PathValue("type") Integer type, @PathValue("id") String id);

    /**
     * 移除一个emoji
     *
     * @param cid
     * @param mid
     * @param type
     * @param id
     * @return
     */
    @RequestPath(method = Connection.Method.DELETE, value = "/channels/{channel_id}/messages/{message_id}/reactions/{type}/{id}")
    void removeEmoji(@PathValue("channel_id") String cid, @PathValue("message_id") String mid, @PathValue("type") Integer type, @PathValue("id") String id);


    /**
     * 添加至精华消息
     *
     * @param cid
     * @param mid
     * @return
     */
    @RequestPath(method = Connection.Method.PUT, value = "/channels/{channel_id}/pins/{message_id}")
    String addPins(@PathValue("channel_id") String cid, @PathValue("message_id") String mid);


    /**
     * 移除精华消息
     *
     * @param cid
     * @param mid all 为全部
     * @return
     */
    @RequestPath(method = Connection.Method.DELETE, value = "/channels/{channel_id}/pins/{message_id}")
    PinsMessage deletePins(@PathValue("channel_id") String cid, @PathValue("message_id") String mid);

    /**
     * 获取精华消息
     *
     * @param cid
     * @return
     */
    @GetPath("/channels/{channel_id}/pins")
    PinsMessage getPins(@PathValue("channel_id") String cid);


    /**
     * 删除
     *
     * @param cid
     */
    @RequestPath(value = "/channels/{channel_id}", method = Connection.Method.DELETE)
    void delete(@PathValue("channel_id") String cid);
}
