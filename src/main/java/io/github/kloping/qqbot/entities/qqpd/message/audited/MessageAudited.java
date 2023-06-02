package io.github.kloping.qqbot.entities.qqpd.message.audited;

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
    private MessageAuditData data;
    private String message;
}