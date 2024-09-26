package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.CommandEventConfig;
import fi.fabianadrian.webhooklogger.common.event.CommandEventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.ExecuteCommandEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

public final class CommandListener {
	private final WebhookLogger webhookLogger;

	public CommandListener(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	@Listener
	@IsCancelled(Tristate.UNDEFINED)
	public void onCommand(ExecuteCommandEvent.Pre event) {
		CommandEventConfig config = this.webhookLogger.eventsConfig().command();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Optional<Player> playerOptional = event.cause().first(Player.class);
		if (playerOptional.isEmpty()) {
			return;
		}

		EventBuilder builder = new CommandEventBuilder(this.webhookLogger)
				.audience(playerOptional.get())
				.cancelled(event.isCancelled())
				.command(event.command());
		this.webhookLogger.clientManager().send(builder);
	}
}
