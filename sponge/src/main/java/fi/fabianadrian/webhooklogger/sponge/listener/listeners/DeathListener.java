package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.event.DeathEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.util.Tristate;
import org.spongepowered.api.world.Location;

public final class DeathListener extends AbstractListener<DeathEventBuilder> {
	public DeathListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Listener
	@IsCancelled(Tristate.UNDEFINED)
	public void onDeath(DestructEntityEvent.Death event) {
		if (!(event.entity() instanceof Player player)) {
			return;
		}

		DeathEventConfig config = this.webhookLogger.eventsConfig().death();
		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Location<?, ?> loc = player.location();
		DeathEventBuilder builder = new DeathEventBuilder(this.webhookLogger)
				.audience(player)
				.cancelled(event.isCancelled())
				.message(event.message())
				.location(loc.blockX(), loc.blockY(), loc.blockZ());
		queue(builder);
	}
}
