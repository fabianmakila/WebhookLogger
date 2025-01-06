package fi.fabianadrian.webhooklogger.common.config.event;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class ChatEventConfig extends CancellableEventConfig {
	@Comment("""
			The webhook format for chat messages. Available placeholders:
			<name>, <display_name>, <message>, <timestamp>, <cancelled>
			""")
	private String format = "<cancelled>[<timestamp>] <name>: <message>";

	public String format() {
		return this.format;
	}
}
