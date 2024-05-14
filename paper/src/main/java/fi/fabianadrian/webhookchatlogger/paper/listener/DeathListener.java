package fi.fabianadrian.webhookchatlogger.paper.listener;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhookchatlogger.common.event.DeathEventComponentBuilder;
import fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPaper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathListener implements Listener {
	private final WebhookChatLogger wcl;

	public DeathListener(WebhookChatLoggerPaper plugin) {
		this.wcl = plugin.wcl();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		DeathEventConfig config = this.wcl.eventsConfig().death();

		if (!config.cancelled() && event.isCancelled()) {
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
