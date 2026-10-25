package io.github.kloping.qqbot.http;

import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaCompleteRequest;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaImage;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaImageList;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaUploadRequest;
import io.github.kloping.qqbot.entities.qqpd.v2.data.MediaUploadTask;
import io.github.kloping.spt.annotations.http.*;
import org.jsoup.Connection;

/**
 * 素材管理(Markdown 图片预上传)HTTP 接口。
 *
 * <p>官方文档：<a href="https://bot.q.qq.com/wiki/develop/api-v2/server-inter/message/material-management.html">素材管理</a></p>
 *
 * <p>流程：{@link #prepare(MediaUploadRequest)} 申请上传任务，用返回的预签名地址直传图片二进制
 * (见 {@link MediaUploadTask#upload})，再 {@link #complete(MediaCompleteRequest)} 换取
 * {@code qqbot-media://} 引用链接。也可直接使用 {@code Bot#uploadImage(String, byte[])} 一步完成。</p>
 *
 * <p><b>限制：</b>单图不超过 10MB，仅支持 png/jpeg/gif/webp；单机器人上限 100 张(删除可释放)；
 * 引用滑动过期，每次被消息成功引用后 90 天顺延。</p>
 *
 * @author github.kloping
 */
@HttpClient(Starter.NET_POINT)
@Headers("io.github.kloping.qqbot.Start0.getHeaders")
public interface MediaImageBase {
    /**
     * 申请上传图片，返回上传任务标识与预签名直传地址。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/media-images/prepare</td></tr> <tr><td>HTTP Method</td> <td>POST</td></tr></table>
     *
     * @param request 请求体，各字段均必填
     * @return 上传任务，upload_id 4 小时内有效
     */
    @PostPath("/v2/media-images/prepare")
    MediaUploadTask prepare(@RequestBody(type = RequestBody.type.json) MediaUploadRequest request);

    /**
     * 完成上传，校验直传 ETag 后换取 {@code qqbot-media://} 引用链接。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/media-images/complete</td></tr> <tr><td>HTTP Method</td> <td>POST</td></tr></table>
     *
     * @param request 请求体，md5 须为直传响应的 ETag
     * @return 图片信息，含引用链接与短时效预览链接
     */
    @PostPath("/v2/media-images/complete")
    MediaImage complete(@RequestBody(type = RequestBody.type.json) MediaCompleteRequest request);

    /**
     * 分页查询当前有效图片，按上传时间倒序；首页不传 cursor，next_cursor 为空表示末页。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/media-images</td></tr> <tr><td>HTTP Method</td> <td>GET</td></tr></table>
     *
     * @param cursor 分页游标，首次请求可传空
     * @return 图片列表分页结果
     */
    @GetPath("/v2/media-images")
    MediaImageList getImages(@ParamName("cursor") @DefaultValue("") String cursor);

    /**
     * 删除图片，删除后引用链接立即失效，已发送消息不受影响(发送时已转存副本)。
     * 删除不存在的图片返回 40035008。
     *
     * <table><tr><td>HTTP URL</td> <td>/v2/media-images/{image_id}/delete</td></tr> <tr><td>HTTP Method</td> <td>DELETE</td></tr></table>
     *
     * @param imageId 图片标识
     */
    @RequestPath(value = "/v2/media-images/{image_id}/delete", method = Connection.Method.DELETE)
    void deleteImage(@PathValue("image_id") String imageId, @RequestBody(type = RequestBody.type.toString) String empty);
}
