package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.CommandEventConfig;
import fi.fabianadrian.webhooklogger.common.event.CommandEventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public final class CommandListener implements Listener {
	private final WebhookLogger webhookLogger;

	public CommandListener(WebhookLoggerPaper plugin) {
		this.webhookLogger = plugin.webhookLogger();
	}

	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		CommandEventConfig config = this.webhookLogger.eventsConfig().command();

		if (!config.enabled() || !config.logCancelled() && event.isCancelled()) {
			return;
		}

		if (event.getSender() instanceof ConsoleCommandSender) {
			if (!config.logConsole()) {
				return;
			}
		} else if (!config.logOther()) {
			return;
		}

		EventBuilder builder = new CommandEventBuilder(this.webhookLogger)
				.cancelled(event.isCancelled())
				.audience(event.getSender())
				.command(event.getCommand());
		this.webhookLogger.clientManager().send(builder);
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		CommandEventConfig config = this.webhookLogger.eventsConfig().command();

		if (!config.enabled() || !config.logCancelled() && event.isCancelled()) {
			return;
		}

		EventBuilder builder = new CommandEventBuilder(this.webhookLogger)
				.cancelled(event.isCancelled())
				.audience(event.getPlayer())
				.command(event.getMessage());
		this.webhookLogger.clientManager().send(builder);
	}
}
