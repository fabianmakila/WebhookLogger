package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.DeathEventConfig;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.util.Tristate;

public final class DeathListener extends AbstractListener {
	public DeathListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public EventType type() {
		return EventType.DEATH;
	}

	@Listener
	@IsCancelled(Tristate.UNDEFINED)
	public void onDeath(DestructEntityEvent.Death event) {
		if (super.webhooks.isEmpty()) {
			return;
		}

		if (!(event.entity() instanceof Player player)) {
			return;
		}

		DeathEventConfig config = super.webhookLogger.eventsConfig().death();
		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		SpongePlayer spongePlayer = new SpongePlayer((ServerPlayer) player);
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(spongePlayer),
				super.placeholderFactory.cancelled(event.isCancelled()),
				super.placeholderFactory.message(event.message())
		);

		queue(config.format(), builder);
	}
}
