package io.github.kloping.qqbot.api.v2;

import io.github.kloping.qqbot.entities.qqpd.v2.Friend;
import io.github.kloping.qqbot.http.data.StreamMessageData;
import io.github.kloping.qqbot.http.data.V2Result;

/**
 * @author github.kloping
 */
public interface FriendMessageEvent extends FriendEvent, MessageV2Event {
    @Override
    Friend getSender();

    @Override
    Friend getSubject();

    /**
     * 向触发本事件的用户发送一个流式消息分片。
     *
     * @param data 分片数据；未设置 {@code msg_id} 时会自动使用本事件的消息 ID
     * @return 当前分片的发送结果，首片的 id 用作后续分片的 stream_msg_id
     */
    V2Result sendStreamMessage(StreamMessageData data);
}
