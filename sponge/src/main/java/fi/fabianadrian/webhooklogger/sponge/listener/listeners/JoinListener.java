package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.JoinEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public final class JoinListener extends AbstractListener {
	public JoinListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Listener
	public void onJoin(ServerSideConnectionEvent.Join event) {
		JoinEventConfig config = webhookLogger.eventsConfig().join();

		SpongePlayer player = new SpongePlayer(event.player());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.player(player),
				placeholderFactory.message(event.message())
		);

		queue(config.format(), builder);
	}
}
