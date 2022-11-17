package io.github.kloping.qqbot.api.message.audited;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author HRS-Computer
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Message_audit {
	private String audit_id;
}