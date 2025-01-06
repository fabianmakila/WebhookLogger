package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.event.EventType;
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

	@Override
	public EventType type() {
		return EventType.DEATH;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (super.webhooks.isEmpty()) {
			return;
		}

		DeathEventConfig config = super.webhookLogger.eventsConfig().death();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		PaperPlayer player = new PaperPlayer(event.getEntity());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(player),
				super.placeholderFactory.cancelled(event.isCancelled()),
				super.placeholderFactory.message(event.deathMessage())
		);

		queue(config.format(), builder);
	}
}
