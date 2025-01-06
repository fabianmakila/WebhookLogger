package fi.fabianadrian.webhooklogger.common.config.event;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class JoinEventConfig {
	@Comment("""
			The webhook format for when a player joins the server. Available placeholders:
			<name>, <display_name>, <message>, <timestamp>, <address>
			""")
	private String format = "[<timestamp>] <name> joined the game";

	public String format() {
		return this.format;
	}
}
