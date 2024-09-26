package fi.fabianadrian.webhooklogger.common.command;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import org.incendo.cloud.CommandManager;

public abstract class BaseCommand {
	protected final WebhookLogger webhookLogger;
	protected final CommandManager<Commander> manager;

	public BaseCommand(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		this.manager = webhookLogger.commandManager();
	}

	protected org.incendo.cloud.Command.Builder<Commander> rootBuilder() {
		return this.manager.commandBuilder("webhooklogger");
	}

	public abstract void register();
}
