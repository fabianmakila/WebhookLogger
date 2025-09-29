package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.JoinEventConfig;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public final class JoinListener extends AbstractListener {
	public JoinListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public EventType type() {
		return EventType.JOIN;
	}

	@Listener
	public void onJoin(ServerSideConnectionEvent.Join event) {
		if (super.webhooks.isEmpty()) {
			return;
		}

		JoinEventConfig config = super.webhookLogger.eventsConfig().join();

		SpongePlayer player = new SpongePlayer(event.player());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(player),
				super.placeholderFactory.message(event.message())
		);

		queue(config.format(), player, builder);
	}
}
