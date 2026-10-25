package io.github.kloping.qqbot.entities.qqpd.v2.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 已上传图片(素材管理)信息，complete 返回值与查询列表条目共用。
 *
 * <p><b>注意：</b>两个接口实际返回的字段不同——
 * complete 仅返回 image_id、url、ttl、preview_url；
 * 列表查询仅返回 image_id、url、file_name、preview_url、created_at、expires_at(无 ttl)。
 * {@code Bot#uploadImage} 会在上传完成后按入参与本机时间补齐 file_name/created_at/expires_at(近似值)，
 * 未经补齐的原始返回中这些字段为 null 属正常现象。</p>
 *
 * <table><thead><tr><th>属性</th> <th>类型</th> <th>说明</th></tr></thead>
 * <tbody><tr><td>image_id</td> <td>string</td> <td>图片标识，用于查询与删除</td></tr>
 * <tr><td>url</td> <td>string</td> <td>{@code qqbot-media://} 引用链接，可在 Markdown 中反复引用</td></tr>
 * <tr><td>file_name</td> <td>string</td> <td>上传时的文件名，仅列表查询返回(Bot#uploadImage 以入参补齐)</td></tr>
 * <tr><td>ttl</td> <td>int</td> <td>引用有效期(秒)，滑动过期：每次被消息成功引用后顺延，仅 complete 返回</td></tr>
 * <tr><td>preview_url</td> <td>string</td> <td>短时效预览链接，勿持久化存储</td></tr>
 * <tr><td>created_at</td> <td>string</td> <td>上传时间，仅列表查询返回(Bot#uploadImage 以本机时间补齐近似值)；实测为 Unix 秒级时间戳字符串(官方文档标注 RFC3339)</td></tr>
 * <tr><td>expires_at</td> <td>string</td> <td>过期时间，仅列表查询返回(Bot#uploadImage 按 created_at + ttl 补齐近似值)；实测为 Unix 秒级时间戳字符串(官方文档标注 RFC3339)</td></tr></tbody></table>
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class MediaImage {
    /** 引用有效期 90 天，滑动过期 */
    public static final long DEFAULT_TTL_SECONDS = 90L * 24 * 60 * 60;

    @JSONField(name = "image_id")
    private String imageId;
    /** qqbot-media://image/v1/&lt;token&gt; 引用链接 */
    private String url;
    @JSONField(name = "file_name")
    private String fileName;
    private Long ttl;
    @JSONField(name = "preview_url")
    private String previewUrl;
    @JSONField(name = "created_at")
    private String createdAt;
    @JSONField(name = "expires_at")
    private String expiresAt;

    /**
     * 生成带尺寸的 Markdown 图片引用，如 {@code ![分时K线 #800px #400px](qqbot-media://image/v1/xxx)}。
     *
     * @param alt    图片说明
     * @param width  显示宽度(px)
     * @param height 显示高度(px)
     * @return Markdown 图片语法
     */
    public String toMarkdown(String alt, int width, int height) {
        return String.format("![%s #%dpx #%dpx](%s)", alt, width, height, url);
    }

    /**
     * 生成不带尺寸的 Markdown 图片引用。
     *
     * @param alt 图片说明
     * @return Markdown 图片语法
     */
    public String toMarkdown(String alt) {
        return String.format("![%s](%s)", alt, url);
    }

    /**
     * 上传时间的 Unix 秒级时间戳，仅列表查询返回，缺失或非法时为 null。
     */
    public Long getCreatedAtSeconds() {
        return parseEpochSeconds(createdAt);
    }

    /**
     * 过期时间的 Unix 秒级时间戳，仅列表查询返回，缺失或非法时为 null。
     * 可与当前时间比较判断剩余有效期。
     */
    public Long getExpiresAtSeconds() {
        return parseEpochSeconds(expiresAt);
    }

    /**
     * 上传时间，由 Unix 秒级时间戳转换而来，仅列表查询返回，缺失或非法时为 null。
     */
    public Date getCreatedAtDate() {
        return toDate(getCreatedAtSeconds());
    }

    /**
     * 过期时间，由 Unix 秒级时间戳转换而来，仅列表查询返回，缺失或非法时为 null。
     * 可用 {@link Date#before}/{@link Date#after} 直接与当前时间比较判断剩余有效期。
     */
    public Date getExpiresAtDate() {
        return toDate(getExpiresAtSeconds());
    }

    private static Long parseEpochSeconds(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Date toDate(Long epochSeconds) {
        return epochSeconds == null ? null : new Date(epochSeconds * 1000L);
    }

    /**
     * 补齐 complete 未返回的字段：fileName 取入参，createdAt 取本机当前时间，expiresAt = createdAt + ttl。
     *
     * <p>除 fileName 外补齐值为近似值(取本机时钟，与服务端可能有秒级偏差)，
     * 准确值请通过列表查询按 image_id 匹配获取。</p>
     *
     * @param fileName 上传时的文件名
     * @return this
     */
    public MediaImage fillMissingInfo(String fileName) {
        setFileName(fileName);
        long nowSeconds = System.currentTimeMillis() / 1000;
        setCreatedAt(String.valueOf(nowSeconds));
        if (ttl != null) {
            setExpiresAt(String.valueOf(nowSeconds + ttl));
        }
        return this;
    }
}
