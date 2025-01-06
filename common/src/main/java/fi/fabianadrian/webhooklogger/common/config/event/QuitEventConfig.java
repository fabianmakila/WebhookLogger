package fi.fabianadrian.webhooklogger.common.config.event;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class QuitEventConfig {
	@Comment("""
			The webhook format for when a player leaves the server. Available placeholders:
			<name>, <display_name>, <message>, <timestamp>
			""")
	private String format = "[<timestamp>] <name> left the game";

	public String format() {
		return this.format;
	}
}
