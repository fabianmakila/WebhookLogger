package fi.fabianadrian.webhooklogger.common.config.event;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class DeathEventConfig extends CancellableEventConfig {
	@Comment("""
			The webhook format for when a player dies. Available placeholders:
			<name>, <display_name>, <message>, <timestamp>, <cancelled>, <location>
			""")
	private String format = "<cancelled>[<timestamp>] <message> (<location>)";

	public String format() {
		return this.format;
	}
}
