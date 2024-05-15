package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookChatLogger;
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
	private final WebhookChatLogger wcl;

	public DeathListener(WebhookLoggerPaper plugin) {
		this.wcl = plugin.wcl();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		DeathEventConfig config = this.wcl.eventsConfig().death();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Location loc = event.getEntity().getLocation();

		Component component = new DeathEventComponentBuilder(this.wcl)
				.audience(event.getEntity())
				.cancelled(event.isCancelled())
				.message(PlainTextComponentSerializer.plainText().serialize(event.deathMessage()))
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.build();
		this.wcl.clientManager().send(component);
	}
}