package fi.fabianadrian.webhooklogger.common.command;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import org.incendo.cloud.CommandManager;

public abstract class BaseCommand {
	protected final WebhookLogger webhookLogger;
	protected final CommandManager<Audience> manager;

	public BaseCommand(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		manager = webhookLogger.commandManager();
	}

	protected org.incendo.cloud.Command.Builder<Audience> rootBuilder() {
		return manager.commandBuilder("webhooklogger");
	}

	public abstract void register();
}
