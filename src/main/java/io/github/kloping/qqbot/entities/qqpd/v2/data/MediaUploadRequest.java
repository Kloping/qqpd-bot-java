package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.security.MessageDigest;

/**
 * 申请上传图片(素材管理)请求体。
 *
 * <p>官方文档：<a href="https://bot.q.qq.com/wiki/develop/api-v2/server-inter/message/material-management.html">素材管理</a></p>
 *
 * <table><thead><tr><th>属性</th> <th>类型</th> <th>必填</th> <th>说明</th></tr></thead>
 * <tbody><tr><td>file_name</td> <td>string</td> <td>是</td> <td>文件名，须含扩展名且与 content_type 一致</td></tr>
 * <tr><td>file_size</td> <td>uint64</td> <td>是</td> <td>文件字节数，不超过 10MB</td></tr>
 * <tr><td>content_type</td> <td>string</td> <td>是</td> <td>image/png、image/jpeg、image/gif、image/webp</td></tr>
 * <tr><td>md5</td> <td>string</td> <td>是</td> <td>文件 MD5，32 位十六进制</td></tr>
 * <tr><td>sha1</td> <td>string</td> <td>是</td> <td>文件 SHA1，40 位十六进制</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class MediaUploadRequest {
    public static final String CONTENT_TYPE_PNG = "image/png";
    public static final String CONTENT_TYPE_JPEG = "image/jpeg";
    public static final String CONTENT_TYPE_GIF = "image/gif";
    public static final String CONTENT_TYPE_WEBP = "image/webp";

    @JSONField(name = "file_name")
    private String fileName;
    @JSONField(name = "file_size")
    private Long fileSize;
    @JSONField(name = "content_type")
    private String contentType;
    private String md5;
    private String sha1;

    /**
     * 根据扩展名推断 content_type，并对内容计算 md5 与 sha1。
     *
     * @param fileName 文件名，须含 png/jpg/jpeg/gif/webp 扩展名
     * @param bytes    图片二进制内容
     * @return 可直接用于 {@code /v2/media-images/prepare} 的请求体
     * @throws IllegalArgumentException 扩展名不支持时抛出
     */
    public static MediaUploadRequest of(String fileName, byte[] bytes) {
        MediaUploadRequest request = new MediaUploadRequest()
                .setFileName(fileName)
                .setContentType(contentTypeOf(fileName))
                .setFileSize((long) bytes.length)
                .setMd5(hex("MD5", bytes))
                .setSha1(hex("SHA-1", bytes));
        return request;
    }

    /**
     * 按扩展名推断 content_type，扩展名与 content_type 不符会被服务端拒绝(40035001)。
     *
     * @param fileName 文件名
     * @return 对应的 image/* content_type
     */
    public static String contentTypeOf(String fileName) {
        String name = fileName == null ? "" : fileName.toLowerCase();
        if (name.endsWith(".png")) return CONTENT_TYPE_PNG;
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return CONTENT_TYPE_JPEG;
        if (name.endsWith(".gif")) return CONTENT_TYPE_GIF;
        if (name.endsWith(".webp")) return CONTENT_TYPE_WEBP;
        throw new IllegalArgumentException(String.format("unsupported image file name: %s", fileName));
    }

    private static String hex(String algorithm, byte[] bytes) {
        try {
            StringBuilder sb = new StringBuilder();
            for (byte b : MessageDigest.getInstance(algorithm).digest(bytes)) {
                sb.append(Character.forDigit((b >> 4) & 0xF, 16)).append(Character.forDigit(b & 0xF, 16));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(String.format("compute %s failed", algorithm), e);
        }
    }
}
