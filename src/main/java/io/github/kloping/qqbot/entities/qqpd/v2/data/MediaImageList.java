package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 有效图片列表分页结果，按上传时间倒序，{@code next_cursor} 为空表示已到末页。
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class MediaImageList {
    private List<MediaImage> images;
    @JSONField(name = "next_cursor")
    private String nextCursor;
    private Integer total;
}
