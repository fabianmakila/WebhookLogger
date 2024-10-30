package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.JoinEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class JoinListener extends AbstractListener<JoinEventBuilder> implements Listener {
	public JoinListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Location loc = event.getPlayer().getLocation();
		JoinEventBuilder builder = new JoinEventBuilder(webhookLogger)
				.audience(event.getPlayer())
				.message(event.joinMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
				.address(event.getPlayer().getAddress());
		queue(builder);
	}
}
