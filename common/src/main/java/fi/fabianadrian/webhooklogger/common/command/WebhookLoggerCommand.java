package fi.fabianadrian.webhooklogger.common.command;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;

public abstract class WebhookLoggerCommand {
	protected final WebhookLogger webhookLogger;
	protected final CommandManager<Commander> manager;

	public WebhookLoggerCommand(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		this.manager = webhookLogger.commandManager();
	}

	protected Command.Builder<Commander> rootBuilder() {
		return this.manager.commandBuilder("webhooklogger");
	}

	public abstract void register();
}
