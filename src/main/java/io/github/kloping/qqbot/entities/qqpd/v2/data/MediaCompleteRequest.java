package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 完成上传图片(素材管理)请求体。
 *
 * <p>{@code md5} 须为直传响应的 ETag(即 {@link MediaUploadTask#upload} 的返回值)，
 * 与 COS 实际 ETag 不符时返回 40035003。</p>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class MediaCompleteRequest {
    @JSONField(name = "upload_id")
    private String uploadId;
    private String md5;
}
