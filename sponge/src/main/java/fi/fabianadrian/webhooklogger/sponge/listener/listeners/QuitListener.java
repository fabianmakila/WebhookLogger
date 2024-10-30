package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.world.Location;

public final class QuitListener extends AbstractListener<QuitEventBuilder> {
	public QuitListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Listener
	public void onQuit(ServerSideConnectionEvent.Disconnect event) {
		Location<?, ?> loc = event.player().location();

		QuitEventBuilder builder = new QuitEventBuilder(webhookLogger)
				.audience(event.player())
				.message(event.message())
				.location(loc.blockX(), loc.blockY(), loc.blockZ());
		queue(builder);
	}
}
