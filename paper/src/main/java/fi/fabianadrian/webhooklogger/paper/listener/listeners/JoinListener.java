package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.JoinEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.paper.platform.PaperPlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class JoinListener extends AbstractListener implements Listener {
	public JoinListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		JoinEventConfig config = webhookLogger.eventsConfig().join();

		PaperPlayer player = new PaperPlayer(event.getPlayer());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.player(player),
				placeholderFactory.message(event.joinMessage())
		);

		queue(config.format(), builder);
	}
}
