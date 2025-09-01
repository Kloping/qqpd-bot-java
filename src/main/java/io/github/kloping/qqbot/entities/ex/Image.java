package io.github.kloping.qqbot.entities.ex;

import lombok.experimental.Accessors;

/**
 * 图片消息 支持jpg/png
 * <br>发送到频道时url必须备案(少数情况不需要
 * <br>可使用 bytes发送
 *
 * @author github.kloping
 */
@Accessors(chain = true)
public class Image extends FileMsg {

    public Image(byte[] bytes) {
        super(bytes, FileType.IMAGE);
    }

    public Image(String url) {
        super(url, FileType.IMAGE);
    }

    public Image(byte[] bytes, String name) {
        super(bytes, FileType.IMAGE);
        this.name = name;
    }

    public Image(String url, String name) {
        super(url, FileType.IMAGE);
        this.name = name;
    }
}
