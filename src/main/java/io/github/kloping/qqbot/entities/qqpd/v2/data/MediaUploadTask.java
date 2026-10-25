package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.qqbot.api.exc.RequestException;
import lombok.Data;
import lombok.experimental.Accessors;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

/**
 * 申请上传图片(素材管理)的返回，持有上传任务标识与预签名直传地址。
 *
 * <table><thead><tr><th>属性</th> <th>类型</th> <th>说明</th></tr></thead>
 * <tbody><tr><td>upload_id</td> <td>string</td> <td>上传任务标识，4 小时内有效</td></tr>
 * <tr><td>presigned_url</td> <td>string</td> <td>预签名直传地址(COS)，直传时不携带鉴权头</td></tr>
 * <tr><td>expires_in</td> <td>int</td> <td>预签名地址有效期(秒)</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class MediaUploadTask {
    /** 上传任务有效期 4 小时，过期后 complete 返回 40035003 */
    public static final long UPLOAD_ID_TTL_SECONDS = 4 * 60 * 60;

    @JSONField(name = "upload_id")
    private String uploadId;
    @JSONField(name = "presigned_url")
    private String presignedUrl;
    @JSONField(name = "expires_in")
    private Long expiresIn;

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    /**
     * 将图片二进制直传至预签名地址，Content-Type 须与申请时声明一致，否则返回 403。
     *
     * <p>预签名地址为 COS 绝对地址，不走 SDK 的注解代理与鉴权头。</p>
     *
     * @param bytes       图片二进制内容
     * @param contentType 申请上传时声明的 content_type
     * @return 响应 ETag(即文件 MD5)，供 complete 校验使用
     * @throws RequestException 直传响应非 2xx 时抛出
     */
    public String upload(byte[] bytes, String contentType) {
        Request request = new Request.Builder()
                .url(presignedUrl)
                .put(RequestBody.create(bytes, MediaType.parse(contentType)))
                .build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            String body = response.body() == null ? "" : response.body().string();
            if (!response.isSuccessful()) {
                throw new RequestException(response.code(), body, presignedUrl, "PUT");
            }
            String etag = response.header("ETag");
            return etag == null ? null : etag.replace("\"", "");
        } catch (RequestException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(String.format("presigned upload failed: %s", presignedUrl), e);
        }
    }
}
