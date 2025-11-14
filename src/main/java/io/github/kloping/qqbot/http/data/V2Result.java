package io.github.kloping.qqbot.http.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.github.kloping.judge.Judge;
import io.github.kloping.qqbot.entities.ex.FileMsg;
import io.github.kloping.qqbot.entities.exceptions.FileMsgUploadFailedException;
import io.github.kloping.spt.interfaces.Logger;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 官方文档
 * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>id</td> <td>string</td> <td>消息唯一ID</td></tr> <tr><td>timestamp</td> <td>int</td> <td>发送时间</td></tr></tbody></table>
 *   实际则
 *   <hr>
 *   {"group_code":"","ret":1,"msg":""}
 * @author github.kloping
 */
@Data
public class V2Result {
    private String id;
    private Long timestamp;

    private String group_code;
    private Integer ret = 200;
    private String msg;

    private String file_uuid;
    private String file_info;
    private Integer ttl;

    public static String docMsg(String json) {
        V2Result result = JSON.parseObject(json, V2Result.class);
        if (Judge.isNotEmpty(result.getMsg())) return result.getMsg();
        else return "";
    }

    public static String docFiles(String json) {
        JSONObject data = JSON.parseObject(json);
        if (Judge.isEmpty(data.getString("file_uuid"))) return "";
        else return data.getString("file_uuid");
    }

    public void logFileInfo(Logger logger, FileMsg image) {
        if (file_uuid == null)
            throw new FileMsgUploadFailedException(String.format("Failed to upload image(%s)", image.getUrl()));
        logger.info("file uuid: " + file_uuid);
    }

    @JSONField(deserialize = false, serialize = false)
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    public void setTimestamp(String timestamp) {
        try {
            Long t0 = format.parse(timestamp).getTime();
            this.timestamp = t0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
