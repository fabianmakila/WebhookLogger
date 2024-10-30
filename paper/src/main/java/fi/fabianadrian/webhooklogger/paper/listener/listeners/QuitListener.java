package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.QuitEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.paper.platform.PaperPlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class QuitListener extends AbstractListener implements Listener {
	public QuitListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		QuitEventConfig config = webhookLogger.eventsConfig().quit();

		PaperPlayer player = new PaperPlayer(event.getPlayer());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.player(player),
				placeholderFactory.message(event.quitMessage())
		);

		queue(config.format(), builder);
	}
}
