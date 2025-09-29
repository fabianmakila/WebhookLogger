package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.QuitEventConfig;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.paper.platform.PaperPlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class QuitListener extends AbstractListener implements Listener {
	public QuitListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public EventType type() {
		return EventType.QUIT;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		if (super.webhooks.isEmpty()) {
			return;
		}

		QuitEventConfig config = super.webhookLogger.eventsConfig().quit();

		PaperPlayer player = new PaperPlayer(event.getPlayer());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(player),
				super.placeholderFactory.message(event.quitMessage())
		);

		queue(config.format(), player, builder);
	}
}
