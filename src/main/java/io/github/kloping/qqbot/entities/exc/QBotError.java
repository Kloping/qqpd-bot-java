package io.github.kloping.qqbot.entities.exc;

import lombok.Data;

/**
 * @author github.kloping
 */
@Data
public class QBotError {
    private String message;
    private Integer code;
    private Integer err_code;
    private String trace_id;
}
