package io.github.kloping.qqbot.api.message.audited;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@lombok.Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class Data {
	private Message_audit message_audit;

}