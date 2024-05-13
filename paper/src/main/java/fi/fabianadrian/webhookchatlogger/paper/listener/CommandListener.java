package fi.fabianadrian.webhookchatlogger.paper.listener;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.section.CommandSection;
import fi.fabianadrian.webhookchatlogger.common.loggable.LoggableCommand;
import fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPaper;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public final class CommandListener implements Listener {
	private final WebhookChatLogger wcl;

	public CommandListener(WebhookChatLoggerPaper plugin) {
		this.wcl = plugin.wcl();
	}

	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		CommandSection config = this.wcl.config().command();

		if (!(event.getSender() instanceof ConsoleCommandSender) || !config.console() || !config.cancelled() && event.isCancelled()) {
			return;
		}

		LoggableCommand command = new LoggableCommand(event.getSender(), event.getCommand(), event.isCancelled());
		this.wcl.clientManager().send(command);
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		CommandSection config = this.wcl.config().command();

		if (!config.cancelled() && event.isCancelled()) {
			return;
		}

		LoggableCommand command = new LoggableCommand(event.getPlayer(), event.getMessage(), event.isCancelled());
		this.wcl.clientManager().send(command);
	}
}
