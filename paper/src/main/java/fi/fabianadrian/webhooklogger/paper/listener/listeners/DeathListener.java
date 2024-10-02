package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.event.DeathEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathListener extends AbstractListener<DeathEventBuilder> implements Listener {
	public DeathListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		DeathEventConfig config = webhookLogger.eventsConfig().death();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Location loc = event.getEntity().getLocation();

		DeathEventBuilder builder = new DeathEventBuilder(webhookLogger)
				.audience(event.getEntity())
				.cancelled(event.isCancelled())
				.message(event.deathMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		queue(builder);
	}
}
