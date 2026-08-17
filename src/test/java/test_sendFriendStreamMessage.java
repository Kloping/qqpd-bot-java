import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.v2.FriendMessageEvent;
import io.github.kloping.qqbot.http.data.StreamMessageData;
import io.github.kloping.qqbot.http.data.V2Result;
import io.github.kloping.qqbot.impl.ListenerHost;

/**
 * @author github.kloping
 */
public class test_sendFriendStreamMessage {
    public static void main(String[] args) throws Exception {
        Starter starter = test_main.factory();
        starter.run();
        starter.registerListenerHost(new ListenerHost() {
            @EventReceiver
            public void onFriendStreamMessage(FriendMessageEvent event) throws InterruptedException {
                String[] line = "这是,一段话,每秒从1...10,1,2,3,4,5,6,7,8,9,10".split(",");
                int index = 0;
                V2Result result = null;
                for (String s : line) {
                    StreamMessageData streamMessageData = null;
                    if (index == 0) {
                        streamMessageData = new StreamMessageData()
                                .setIndex(0)
                                .setContent_raw(s);
                    } else {
                        streamMessageData = new StreamMessageData()
                                .setIndex(index)
                                .setContent_raw(s)
                                .setStream_msg_id(result.getId());
                    }
                    index++;
                    result = event.sendStreamMessage(streamMessageData);
                    System.out.println(result);
                    Thread.sleep(700);
                }

                StreamMessageData streamMessageData = new StreamMessageData()
                        .setIndex(index)
                        .setContent_raw(".")
                        .setStream_msg_id(result.getId())
                        .setInput_state(StreamMessageData.INPUT_STATE_FINISHED);
                index++;
                result = event.sendStreamMessage(streamMessageData);
                System.out.println(result);
            }
        });
    }
}
