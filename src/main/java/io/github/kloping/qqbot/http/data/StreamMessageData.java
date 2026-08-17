package io.github.kloping.qqbot.http.data;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 单聊流式消息分片请求体。
 *
 * <p>同一条流的全部分片使用相同的 {@link #stream_msg_id}，并使
 * {@link #index} 从 0 递增。{@code input_mode=replace} 时，
 * {@code content_raw} 必须以已发送内容为前缀。</p>
 */
@Data
@Accessors(chain = true)
public class StreamMessageData {
    public static final String INPUT_MODE_APPEND = "append";
    public static final String INPUT_MODE_REPLACE = "replace";
    public static final String CONTENT_TYPE_TEXT = "text";
    public static final String CONTENT_TYPE_MARKDOWN = "markdown";
    public static final int INPUT_STATE_GENERATING = 1;
    public static final int INPUT_STATE_FINISHED = 10;

    private String input_mode = INPUT_MODE_APPEND;
    private Integer input_state = INPUT_STATE_GENERATING;
    private Integer index = 0;
    private String content_type = CONTENT_TYPE_TEXT;
    private String content_raw = "";
    private String event_id;
    private String msg_id;
    private String stream_msg_id;
    private Integer msg_seq = 1;
    private Boolean is_wakeup;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
