package fi.fabianadrian.webhookchatlogger.sponge.listener;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.loggable.LoggableCommand;
import fi.fabianadrian.webhookchatlogger.sponge.WebhookChatLoggerSponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.ExecuteCommandEvent;

import java.util.Optional;

public class CommandListener {
	private final WebhookChatLogger wcl;

	public CommandListener(WebhookChatLoggerSponge plugin) {
		this.wcl = plugin.wcl();
	}

	@Listener
	public void onCommand(ExecuteCommandEvent event) {
		Optional<Player> playerOptional = event.cause().first(Player.class);
		if (playerOptional.isEmpty()) {
			return;
		}

		LoggableCommand command = new LoggableCommand(playerOptional.get(), event.command(), false);
		this.wcl.clientManager().send(command);
	}
}
