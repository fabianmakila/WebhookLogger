package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.event.DeathEventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathListener implements Listener {
	private final WebhookLogger webhookLogger;

	public DeathListener(WebhookLoggerPaper plugin) {
		this.webhookLogger = plugin.webhookLogger();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		DeathEventConfig config = this.webhookLogger.eventsConfig().death();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Location loc = event.getEntity().getLocation();

		EventBuilder builder = new DeathEventBuilder(this.webhookLogger)
				.audience(event.getEntity())
				.cancelled(event.isCancelled())
				.message(event.deathMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		this.webhookLogger.clientManager().send(builder);
	}
}
