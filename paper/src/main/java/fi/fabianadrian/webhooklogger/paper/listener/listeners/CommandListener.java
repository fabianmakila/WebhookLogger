package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.CommandEventConfig;
import fi.fabianadrian.webhooklogger.common.event.CommandEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public final class CommandListener extends AbstractListener<CommandEventBuilder> implements Listener {
	public CommandListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		CommandEventConfig config = this.webhookLogger.eventsConfig().command();
		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		if (event.getSender() instanceof ConsoleCommandSender) {
			if (!config.logConsole()) {
				return;
			}
		} else if (!config.logOther()) {
			return;
		}

		CommandEventBuilder builder = new CommandEventBuilder(this.webhookLogger)
				.cancelled(event.isCancelled())
				.audience(event.getSender())
				.command(event.getCommand());
		queue(builder);
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		CommandEventConfig config = this.webhookLogger.eventsConfig().command();
		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		CommandEventBuilder builder = new CommandEventBuilder(this.webhookLogger)
				.cancelled(event.isCancelled())
				.audience(event.getPlayer())
				.command(event.getMessage());
		queue(builder);
	}
}
