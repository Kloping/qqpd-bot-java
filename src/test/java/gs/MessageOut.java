package gs;

import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class MessageOut {
    private String bot_id;
    private String bot_self_id;
    private String msg_id;
    private String target_type;
    private String target_id;
    private MessageData[] content;
}
