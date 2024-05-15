package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.JoinQuitEventConfig;
import fi.fabianadrian.webhooklogger.common.event.JoinQuitEventComponentBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class JoinQuitListener implements Listener {
	private final WebhookLogger webhookLogger;

	public JoinQuitListener(WebhookLoggerPaper plugin) {
		this.webhookLogger = plugin.webhookLogger();
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		JoinQuitEventConfig config = this.webhookLogger.eventsConfig().joinQuit();
		if (!config.enabled()) {
			return;
		}

		Component joinMessage = event.joinMessage();
		String joinMessageAsString = joinMessage != null ? PlainTextComponentSerializer.plainText().serialize(joinMessage) : "";
		Location loc = event.getPlayer().getLocation();

		Component component = new JoinQuitEventComponentBuilder(this.webhookLogger)
				.audience(event.getPlayer())
				.message(joinMessageAsString)
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(String.valueOf(event.getPlayer().getAddress()))
				.build();
		this.webhookLogger.clientManager().send(component);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		JoinQuitEventConfig config = this.webhookLogger.eventsConfig().joinQuit();
		if (!config.enabled()) {
			return;
		}

		Component quitMessage = event.quitMessage();
		String quitMessageAsString = quitMessage != null ? PlainTextComponentSerializer.plainText().serialize(quitMessage) : "";
		Location loc = event.getPlayer().getLocation();
		Component component = new JoinQuitEventComponentBuilder(this.webhookLogger)
				.audience(event.getPlayer())
				.message(quitMessageAsString)
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(String.valueOf(event.getPlayer().getAddress()))
				.build();
		this.webhookLogger.clientManager().send(component);
	}
}
