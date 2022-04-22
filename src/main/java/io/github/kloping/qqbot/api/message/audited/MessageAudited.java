package io.github.kloping.qqbot.api.message.audited;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author github-kloping
 */
@lombok.Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class MessageAudited {
    private Number code;
    private Data data;
    private String message;
}