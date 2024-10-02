package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.JoinQuitEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class JoinQuitListener extends AbstractListener<JoinQuitEventBuilder> implements Listener {
	public JoinQuitListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Location loc = event.getPlayer().getLocation();
		JoinQuitEventBuilder builder = new JoinQuitEventBuilder(webhookLogger)
				.audience(event.getPlayer())
				.message(event.joinMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(event.getPlayer().getAddress());
		queue(builder);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Location loc = event.getPlayer().getLocation();

		JoinQuitEventBuilder builder = new JoinQuitEventBuilder(webhookLogger)
				.audience(event.getPlayer())
				.message(event.quitMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(event.getPlayer().getAddress());
		queue(builder);
	}
}
