package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.CommandEventConfig;
import fi.fabianadrian.webhooklogger.common.event.CommandEventComponentBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import net.kyori.adventure.text.Component;
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

		Component component = new CommandEventComponentBuilder(this.webhookLogger)
				.cancelled(event.isCancelled())
				.audience(event.getSender())
				.command(event.getCommand())
				.build();
		this.webhookLogger.clientManager().send(component);
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		CommandEventConfig config = this.webhookLogger.eventsConfig().command();

		if (!config.enabled() || !config.logCancelled() && event.isCancelled()) {
			return;
		}

		Component component = new CommandEventComponentBuilder(this.webhookLogger)
				.cancelled(event.isCancelled())
				.audience(event.getPlayer())
				.command(event.getMessage())
				.build();
		this.webhookLogger.clientManager().send(component);
	}
}
