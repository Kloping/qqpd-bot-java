package io.github.kloping.qqbot.entities.qqpd.message;

import lombok.Data;

/**
 * 附件
 *
 * @author github.kloping
 */
@Data
public class MessageAttachment {
    private String content_type;
    private String filename;
    private String url;
    private String id;
    private Integer height;
    private Integer size;
    private Integer width;
}
