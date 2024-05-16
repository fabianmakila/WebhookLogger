package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.event.DeathEventComponentBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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
		Component deathMessage = event.deathMessage();

		if (!config.enabled() || !config.logCancelled() && event.isCancelled() || deathMessage == null) {
			return;
		}

		Location loc = event.getEntity().getLocation();

		Component component = new DeathEventComponentBuilder(this.webhookLogger)
				.audience(event.getEntity())
				.cancelled(event.isCancelled())
				.message(PlainTextComponentSerializer.plainText().serialize(deathMessage))
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.build();
		this.webhookLogger.clientManager().send(component);
	}
}
