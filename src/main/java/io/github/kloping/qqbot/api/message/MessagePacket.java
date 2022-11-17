package io.github.kloping.qqbot.api.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class MessagePacket {
    /**
     * 文本
     */
    private String content;
    /**
     * 这里上传图片的网址必须是经过备案的域名下的文件 <br/> 否则将报错500 <br/>
     * 图片地址
     */
    private String image;
    /**
     * 引用消息id
     */
    private String replyId;
}
