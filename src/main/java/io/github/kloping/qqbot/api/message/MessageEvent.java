package io.github.kloping.qqbot.api.message;

import io.github.kloping.qqbot.api.Sender;
import io.github.kloping.qqbot.api.event.Event;
import io.github.kloping.qqbot.entities.ex.msg.MessageChain;
import io.github.kloping.qqbot.entities.qqpd.message.RawMessage;
import io.github.kloping.qqbot.entities.qqpd.v2.Contact;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaImage;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaImageList;

/**
 * 消息事件接口
 *
 * @author github.kloping
 */
public interface MessageEvent<T extends Contact, S extends Contact> extends Event, Sender {
    /**
     * 获取消息
     *
     * @return
     */
    RawMessage getRawMessage();

    /**
     * 消息事件发送者
     *
     * @return
     */
    T getSender();

    /**
     * 发送环境
     *
     * @return
     */
    S getSubject();
    /**
     * 将消息转为 MessageChain
     *
     * @return
     */
    MessageChain getMessage();

    /**
     * set filter
     *
     * @param filters
     */
    void setFilter(Class<?>[] filters);

    /**
     * 上传图片素材(预上传 Markdown 图片)，返回可在 Markdown 消息中引用的 {@code qqbot-media://} 链接。
     *
     * <p>详见 {@link io.github.kloping.qqbot.entities.Bot#uploadImage(String, byte[])}。
     * complete 接口仅返回 image_id、url、ttl、preview_url，其余字段按入参与本机时间补齐(近似值)。</p>
     *
     * @param fileName 文件名，须含扩展名
     * @param bytes    图片二进制内容
     * @return 上传结果，可用 {@link MediaImage#toMarkdown(String, int, int)} 生成 Markdown 引用
     */
    default MediaImage uploadImage(String fileName, byte[] bytes) {
        return getBot().uploadImage(fileName, bytes);
    }

    /**
     * 分页查询当前有效图片素材，按上传时间倒序，next_cursor 为空表示已到末页。
     *
     * @param cursor 分页游标，首次请求可传空
     * @return 图片列表分页结果
     */
    default MediaImageList getMediaImages(String cursor) {
        return getBot().getMediaImages(cursor);
    }

    /**
     * 查询当前有效图片素材的第一页。
     *
     * @return 图片列表分页结果
     */
    default MediaImageList getMediaImages() {
        return getMediaImages(null);
    }

    /**
     * 删除指定图片素材，引用链接立即失效，已发送消息不受影响(发送时已转存副本)。
     *
     * @param imageId 图片标识，见 {@link MediaImage#getImageId()}
     */
    default void deleteMediaImage(String imageId) {
        getBot().deleteMediaImage(imageId);
    }

    @Override
    default String getClassName() {
        return MessageEvent.class.getSimpleName();
    }
}
