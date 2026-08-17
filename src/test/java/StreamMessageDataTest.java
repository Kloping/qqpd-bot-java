import com.alibaba.fastjson.JSON;
import io.github.kloping.qqbot.http.data.StreamMessageData;
import org.junit.Assert;
import org.junit.Test;

public class StreamMessageDataTest {
    @Test
    public void shouldSerializeOfficialStreamFields() {
        StreamMessageData data = new StreamMessageData()
                .setInput_mode(StreamMessageData.INPUT_MODE_REPLACE)
                .setInput_state(StreamMessageData.INPUT_STATE_FINISHED)
                .setIndex(2)
                .setContent_type(StreamMessageData.CONTENT_TYPE_MARKDOWN)
                .setContent_raw("最终内容")
                .setMsg_id("message-id")
                .setStream_msg_id("stream-id")
                .setMsg_seq(1);

        Assert.assertEquals("replace", JSON.parseObject(data.toString()).getString("input_mode"));
        Assert.assertEquals(10, JSON.parseObject(data.toString()).getIntValue("input_state"));
        Assert.assertEquals("stream-id", JSON.parseObject(data.toString()).getString("stream_msg_id"));
    }
}
