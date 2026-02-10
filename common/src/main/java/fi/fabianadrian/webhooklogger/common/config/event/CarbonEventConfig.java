package fi.fabianadrian.webhooklogger.common.config.event;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class CarbonEventConfig extends CancellableEventConfig {
	@Comment("""
			The webhook format for carbonchat messages. Available placeholders:
			<name>, <display_name>, <message>, <timestamp>, <cancelled> <key>
			""")
	private String format = "<cancelled>[<timestamp>] [<key>] <name>: <message>";

	public String format() {
		return this.format;
	}
}
