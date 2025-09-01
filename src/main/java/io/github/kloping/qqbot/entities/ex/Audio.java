package io.github.kloping.qqbot.entities.ex;

import lombok.experimental.Accessors;

/**
 * 语音消息 仅支持silk格式
 * <br>发送到频道时url必须备案(少数情况不需要
 * <br>可使用 bytes发送
 *
 * @author github.kloping
 */
@Accessors(chain = true)
public class Audio extends FileMsg {

    public Audio(byte[] bytes) {
        super(bytes, FileType.AUDIO);
    }

    public Audio(String url) {
        super(url, FileType.AUDIO);
    }

    public Audio(byte[] bytes, String name) {
        super(bytes, FileType.AUDIO);
        this.name = name;
    }

    public Audio(String url, String name) {
        super(url, FileType.AUDIO);
        this.name = name;
    }
}
