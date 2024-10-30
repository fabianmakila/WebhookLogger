package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.QuitEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class QuitListener extends AbstractListener<QuitEventBuilder> implements Listener {
	public QuitListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Location loc = event.getPlayer().getLocation();

		QuitEventBuilder builder = new QuitEventBuilder(webhookLogger)
				.audience(event.getPlayer())
				.message(event.quitMessage())
				.location(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		queue(builder);
	}
}
