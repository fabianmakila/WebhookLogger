package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.QuitEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public final class QuitListener extends AbstractListener {
	public QuitListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public Key key() {
		return Key.key("webhooklogger", "quit");
	}

	@Listener
	public void onQuit(ServerSideConnectionEvent.Leave event) {
		QuitEventConfig config = super.webhookLogger.eventsConfig().quit();

		SpongePlayer player = new SpongePlayer(event.player());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(player),
				super.placeholderFactory.message(event.message())
		);

		post(config.format(), player, builder);
	}
}
