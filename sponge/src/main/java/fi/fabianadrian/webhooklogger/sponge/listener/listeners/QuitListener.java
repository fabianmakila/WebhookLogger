package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.QuitEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public final class QuitListener extends AbstractListener {
	public QuitListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Listener
	public void onQuit(ServerSideConnectionEvent.Disconnect event) {
		QuitEventConfig config = webhookLogger.eventsConfig().quit();

		SpongePlayer player = new SpongePlayer(event.player());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.player(player),
				placeholderFactory.message(event.message())
		);

		queue(config.format(), builder);
	}
}
