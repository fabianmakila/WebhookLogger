package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.paper.platform.PaperPlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathListener extends AbstractListener implements Listener {
	public DeathListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		DeathEventConfig config = webhookLogger.eventsConfig().death();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		PaperPlayer player = new PaperPlayer(event.getEntity());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.player(player),
				placeholderFactory.cancelled(event.isCancelled()),
				placeholderFactory.message(event.deathMessage())
		);

		queue(config.format(), builder);
	}
}
