package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.JoinEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public final class JoinListener extends AbstractListener {
	public JoinListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public Key key() {
		return Key.key("webhooklogger", "join");
	}

	@Listener
	public void onJoin(ServerSideConnectionEvent.Join event) {
		JoinEventConfig config = super.webhookLogger.eventsConfig().join();

		SpongePlayer player = new SpongePlayer(event.player());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(player),
				super.placeholderFactory.message(event.message())
		);

		post(config.format(), player, builder);
	}
}
