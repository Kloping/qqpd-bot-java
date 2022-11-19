package io.github.kloping.qqbot.api.qqpd.message.audited;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 *
 * @author github.kloping
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class MessageAudit {
	private String auditId;
}