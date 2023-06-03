package gs;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author github-kloping
 * @date 2023-06-03
 */
@Data
@Accessors(chain = true)
public class MessageReceive {
    private String bot_id;
    private String bot_self_id;
    private String msg_id;
    private String user_type;
    private String group_id;
    private String user_id;
    private Integer user_pm = 3;
    private MessageData[] content;
}