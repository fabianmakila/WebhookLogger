package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.JoinEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.paper.platform.PaperPlayer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class JoinListener extends AbstractListener implements Listener {
	public JoinListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public Key key() {
		return Key.key("webhooklogger", "join");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {
		JoinEventConfig config = super.webhookLogger.eventsConfig().join();

		PaperPlayer player = new PaperPlayer(event.getPlayer());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(player),
				super.placeholderFactory.message(event.joinMessage())
		);

		post(config.format(), player, builder);
	}
}
