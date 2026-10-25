import com.alibaba.fastjson.JSON;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaCompleteRequest;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaImage;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaImageList;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaUploadRequest;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.Assert.*;

/** 素材管理(预上传 Markdown 图片)模型映射回归测试。 */
public class MediaImageModelTest {

    private final byte[] content = "hello".getBytes(StandardCharsets.UTF_8);

    @Test
    public void computesDigestsAndInfersContentType() {
        MediaUploadRequest request = MediaUploadRequest.of("kline.png", content);
        assertEquals("image/png", request.getContentType());
        assertEquals("kline.png", request.getFileName());
        assertEquals(Long.valueOf(content.length), request.getFileSize());
        assertEquals("5d41402abc4b2a76b9719d911017c592", request.getMd5());
        assertEquals("aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d", request.getSha1());
    }

    @Test
    public void serializesPrepareRequestWithSnakeCase() {
        String json = JSON.toJSONString(MediaUploadRequest.of("kline.png", content));
        assertTrue(json, json.contains("\"file_name\":\"kline.png\""));
        assertTrue(json, json.contains("\"file_size\":5"));
        assertTrue(json, json.contains("\"content_type\":\"image/png\""));
        assertTrue(json, json.contains("\"md5\":\"5d41402abc4b2a76b9719d911017c592\""));
        assertTrue(json, json.contains("\"sha1\":\"aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d\""));
        assertFalse(json, json.contains("fileName"));
        assertFalse(json, json.contains("contentType"));
    }

    @Test
    public void infersContentTypeFromExtension() {
        assertEquals("image/png", MediaUploadRequest.contentTypeOf("a.PNG"));
        assertEquals("image/jpeg", MediaUploadRequest.contentTypeOf("a.jpg"));
        assertEquals("image/jpeg", MediaUploadRequest.contentTypeOf("a.jpeg"));
        assertEquals("image/gif", MediaUploadRequest.contentTypeOf("a.gif"));
        assertEquals("image/webp", MediaUploadRequest.contentTypeOf("a.webp"));
        try {
            MediaUploadRequest.contentTypeOf("a.bmp");
            fail("unsupported extension should throw");
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void parsesCompleteResponse() {
        // 实测 complete 仅返回 image_id、url、ttl、preview_url
        String json = "{\"image_id\":\"102032364_75e4a9d72fbadda2\","
                + "\"url\":\"qqbot-media://image/v1/Ri_0F_6kxv3T5mhxi_VAdKHUaw3Kd4DcFavTaHQJ0\","
                + "\"ttl\":7776000,\"preview_url\":\"https://qqbot-file-upload-1251316161.cos.accelerate.myqcloud.com/x\"}";
        MediaImage image = JSON.parseObject(json, MediaImage.class);
        assertEquals("102032364_75e4a9d72fbadda2", image.getImageId());
        assertEquals("qqbot-media://image/v1/Ri_0F_6kxv3T5mhxi_VAdKHUaw3Kd4DcFavTaHQJ0", image.getUrl());
        assertEquals(Long.valueOf(7776000L), image.getTtl());
        assertEquals("https://qqbot-file-upload-1251316161.cos.accelerate.myqcloud.com/x", image.getPreviewUrl());
        assertNull(image.getFileName());
        assertNull(image.getCreatedAt());
        assertNull(image.getExpiresAt());
    }

    @Test
    public void parsesImageListItem() {
        // 实测列表项无 ttl,created_at/expires_at 为 Unix 秒级时间戳字符串
        String json = "{\"image_id\":\"i1\",\"url\":\"qqbot-media://image/v1/t1\",\"file_name\":\"kline.png\","
                + "\"preview_url\":\"https://p\",\"created_at\":\"1792928861\",\"expires_at\":\"1800704861\"}";
        MediaImage image = JSON.parseObject(json, MediaImage.class);
        assertEquals("i1", image.getImageId());
        assertEquals("kline.png", image.getFileName());
        assertNull(image.getTtl());
        assertEquals(Long.valueOf(1792928861L), image.getCreatedAtSeconds());
        assertEquals(Long.valueOf(1800704861L), image.getExpiresAtSeconds());
        assertEquals(7776000L, image.getExpiresAtSeconds() - image.getCreatedAtSeconds());
        assertEquals(new Date(1792928861000L), image.getCreatedAtDate());
        assertEquals(new Date(1800704861000L), image.getExpiresAtDate());
        assertTrue(image.getExpiresAtDate().after(image.getCreatedAtDate()));
        // complete 返回的实体无时间字段
        assertNull(new MediaImage().getCreatedAtDate());
        assertNull(new MediaImage().getExpiresAtDate());
    }

    @Test
    public void parsesImageListPage() {
        // 实测 total 为字符串,fastjson 自动转换
        String json = "{\"total\":\"3\",\"images\":[{\"image_id\":\"i1\",\"url\":\"qqbot-media://image/v1/t1\","
                + "\"file_name\":\"a.png\"}],\"next_cursor\":\"\"}";
        MediaImageList page = JSON.parseObject(json, MediaImageList.class);
        assertEquals(1, page.getImages().size());
        assertEquals("i1", page.getImages().get(0).getImageId());
        assertEquals("", page.getNextCursor());
        assertEquals(Integer.valueOf(3), page.getTotal());
    }

    @Test
    public void serializesCompleteRequest() {
        String json = JSON.toJSONString(new MediaCompleteRequest().setUploadId("u1").setMd5("abc"));
        assertTrue(json, json.contains("\"upload_id\":\"u1\""));
        assertTrue(json, json.contains("\"md5\":\"abc\""));
        assertFalse(json, json.contains("uploadId"));
    }

    @Test
    public void fillsMissingInfoAfterComplete() {
        // complete 仅返回 image_id/url/ttl/preview_url,Bot.uploadImage 会调用 fillMissingInfo 补齐其余字段
        MediaImage image = new MediaImage().setTtl(7776000L).fillMissingInfo("kline.png");
        assertEquals("kline.png", image.getFileName());
        assertNotNull(image.getCreatedAt());
        assertNotNull(image.getExpiresAt());
        assertEquals("expires_at 与 created_at 相差一个 ttl",
                7776000L, image.getExpiresAtSeconds() - image.getCreatedAtSeconds());
        assertNotNull(image.getCreatedAtDate());
        assertNotNull(image.getExpiresAtDate());
        assertTrue(image.getExpiresAtDate().after(image.getCreatedAtDate()));
    }

    @Test
    public void buildsMarkdownReference() {
        MediaImage image = new MediaImage().setUrl("qqbot-media://image/v1/token");
        assertEquals("![分时K线 #800px #400px](qqbot-media://image/v1/token)",
                image.toMarkdown("分时K线", 800, 400));
        assertEquals("![图](qqbot-media://image/v1/token)", image.toMarkdown("图"));
    }
}
