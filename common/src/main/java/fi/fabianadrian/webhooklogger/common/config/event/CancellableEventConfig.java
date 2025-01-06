package fi.fabianadrian.webhooklogger.common.config.event;

import org.spongepowered.configurate.objectmapping.meta.Comment;

public class CancellableEventConfig {
	@Comment("Whether this event should be logged even when cancelled.")
	private boolean logCancelled = true;

	public boolean logCancelled() {
		return logCancelled;
	}
}
