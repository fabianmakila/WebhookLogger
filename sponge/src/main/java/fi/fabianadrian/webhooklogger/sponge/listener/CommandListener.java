package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.CommandEventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.sponge.WebhookLoggerSponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.ExecuteCommandEvent;

import java.util.Optional;

public final class CommandListener {
	private final WebhookLogger webhookLogger;

	public CommandListener(WebhookLoggerSponge plugin) {
		this.webhookLogger = plugin.webhookLogger();
	}

	@Listener
	public void onCommand(ExecuteCommandEvent event) {
		Optional<Player> playerOptional = event.cause().first(Player.class);
		if (playerOptional.isEmpty()) {
			return;
		}

		EventBuilder builder = new CommandEventBuilder(this.webhookLogger)
				.audience(playerOptional.get())
				.command(event.command());
		this.webhookLogger.clientManager().send(builder);
	}
}
