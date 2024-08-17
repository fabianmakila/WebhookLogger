package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.event.DeathEventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.Location;

public final class DeathListener {
	private final WebhookLogger webhookLogger;

	public DeathListener(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	@Listener
	@IsCancelled(Tristate.UNDEFINED)
	public void onDeath(DestructEntityEvent.Death event) {
		if (!(event.entity() instanceof Player player)) {
			return;
		}

		DeathEventConfig config = this.webhookLogger.eventsConfig().death();
		if (!config.enabled() || !config.logCancelled() && event.isCancelled()) {
			return;
		}

		Location<?, ?> loc = player.location();
		EventBuilder builder = new DeathEventBuilder(this.webhookLogger)
				.audience(player)
				.cancelled(event.isCancelled())
				.message(event.message())
				.location(loc.blockX(), loc.blockY(), loc.blockZ());
		this.webhookLogger.clientManager().send(builder);
	}
}
