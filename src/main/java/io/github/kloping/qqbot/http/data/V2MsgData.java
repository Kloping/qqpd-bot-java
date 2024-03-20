package io.github.kloping.qqbot.http.data;

import com.alibaba.fastjson.JSON;
import io.github.kloping.qqbot.entities.ex.Markdown;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * <hr>
 * * <table><thead><tr><th><strong>属性</strong></th> <th><strong>类型</strong></th> <th><strong>必填</strong></th> <th><strong>说明</strong></th></tr></thead> <tbody><tr><td>content</td> <td>string</td> <td>是</td> <td>文本内容</td></tr> <tr><td>msg_type</td> <td>int</td> <td>是</td> <td>消息类型： 0 是文本，1 图文混排 ，2 是 markdown 3 ark，4 embed</td></tr> <tr><td>markdown</td> <td>object</td> <td>否</td> <td>格式参考"消息类型=&gt;markdown=&gt;数据结构与协议"</td></tr> <tr><td>keyboard</td> <td>object</td> <td>否</td> <td>格式参考"消息交互=&gt;消息按钮=&gt;数据结构与协"</td></tr> <tr><td>ark</td> <td>object</td> <td>否</td> <td>格式参考"消息类型=&gt;ark=&gt;数据结构与协议"</td></tr> <tr><td>image</td> <td></td> <td>否</td> <td>【暂不支持】</td></tr> <tr><td>message_reference</td> <td>object</td> <td>否</td> <td>【暂未支持】消息引用</td></tr> <tr><td>event_id</td> <td>string</td> <td>否</td> <td>【暂未支持】前置收到的事件ID，用于发送被动消息</td></tr> <tr><td>msg_id</td> <td>string</td> <td>否</td> <td>前置收到的消息ID，用于发送被动消息</td></tr></tbody></table>
 * *
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
public class V2MsgData {
    private String content = "";
    private Integer msg_type = 0;
    private String image = null;
    private Media media;
    private Markdown markdown;
    private Object keyboard;
    private String msg_id;
    private Integer msg_seq;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Getter
    public static class Media {
        private String file_info;

        public Media(String file_info) {
            this.file_info = file_info;
        }
    }
}
