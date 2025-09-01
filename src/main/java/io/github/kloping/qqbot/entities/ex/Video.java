package io.github.kloping.qqbot.entities.ex;

import lombok.experimental.Accessors;

/**
 * 视频消息 仅支持mp4格式
 * <br>发送到频道时url必须备案(少数情况不需要
 * <br>可使用 bytes发送
 *
 * @author github.kloping
 */
@Accessors(chain = true)
public class Video extends FileMsg {

    public Video(byte[] bytes) {
        super(bytes, FileType.VIDEO);
    }

    public Video(String url) {
        super(url, FileType.VIDEO);
    }

    public Video(byte[] bytes, String name) {
        super(bytes, FileType.VIDEO);
        this.name = name;
    }

    public Video(String url, String name) {
        super(url, FileType.VIDEO);
        this.name = name;
    }
}
