package fi.fabianadrian.webhookchatlogger.paper.listener;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.event.CommandEventConfig;
import fi.fabianadrian.webhookchatlogger.common.event.CommandEventComponentBuilder;
import fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPaper;
import net.kyori.adventure.text.Component;
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
		CommandEventConfig config = this.wcl.eventsConfig().command();

		if (event.getSender() instanceof ConsoleCommandSender) {
			if (!config.logConsole()) {
				return;
			}
		} else if (!config.logCancelled() && event.isCancelled() || !config.logOther()) {
			return;
		}

		Component component = new CommandEventComponentBuilder(this.wcl)
				.cancelled(event.isCancelled())
				.audience(event.getSender())
				.command(event.getCommand())
				.build();
		this.wcl.clientManager().send(component);
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		CommandEventConfig config = this.wcl.eventsConfig().command();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Component component = new CommandEventComponentBuilder(this.wcl)
				.cancelled(event.isCancelled())
				.audience(event.getPlayer())
				.command(event.getMessage())
				.build();
		this.wcl.clientManager().send(component);
	}
}
