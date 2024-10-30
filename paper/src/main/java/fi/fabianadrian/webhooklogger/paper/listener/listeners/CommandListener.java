package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.CommandEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.paper.platform.PaperPlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public final class CommandListener extends AbstractListener implements Listener {
	public CommandListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onServerCommand(ServerCommandEvent event) {
		CommandEventConfig config = webhookLogger.eventsConfig().command();
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

		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.audience(event.getSender()),
				placeholderFactory.cancelled(event.isCancelled()),
				placeholderFactory.command(event.getCommand())
		);

		queue(config.format(), builder);
	}

	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		CommandEventConfig config = webhookLogger.eventsConfig().command();
		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		PaperPlayer player = new PaperPlayer(event.getPlayer());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.player(player),
				placeholderFactory.cancelled(event.isCancelled()),
				placeholderFactory.command(event.getMessage())
		);

		queue(config.format(), builder);
	}
}
