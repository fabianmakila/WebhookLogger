package fi.fabianadrian.webhooklogger.common.config.event;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class CommandEventConfig extends CancellableEventConfig {
	@Comment("""
			The webhook format for executed commands. Available placeholders:
			<name>, <display_name>, <command>, <timestamp>, <cancelled>
			""")
	private String format = "<cancelled>[<timestamp>] <display_name>: <command>";

	@Comment("Whether console commands will be logged.")
	private boolean logConsole = false;

	@Comment("Whether other entities commands will be logged.")
	private boolean logOther = false;

	public String format() {
		return this.format;
	}

	public boolean logConsole() {
		return this.logConsole;
	}

	public boolean logOther() {
		return this.logOther;
	}
}
