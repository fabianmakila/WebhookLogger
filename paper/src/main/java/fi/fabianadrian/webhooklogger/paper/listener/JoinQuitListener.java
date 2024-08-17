package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.JoinQuitEventConfig;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.common.event.JoinQuitEventBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
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

		Location loc = event.getPlayer().getLocation();
		EventBuilder builder = new JoinQuitEventBuilder(this.webhookLogger)
				.audience(event.getPlayer())
				.message(event.joinMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(event.getPlayer().getAddress());
		this.webhookLogger.clientManager().send(builder);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		JoinQuitEventConfig config = this.webhookLogger.eventsConfig().joinQuit();
		if (!config.enabled()) {
			return;
		}

		Location loc = event.getPlayer().getLocation();

		EventBuilder builder = new JoinQuitEventBuilder(this.webhookLogger)
				.audience(event.getPlayer())
				.message(event.quitMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(event.getPlayer().getAddress());
		this.webhookLogger.clientManager().send(builder);
	}
}
