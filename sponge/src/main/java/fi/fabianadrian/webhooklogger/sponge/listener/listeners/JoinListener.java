package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.JoinEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.world.Location;

public final class JoinListener extends AbstractListener<JoinEventBuilder> {
	public JoinListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Listener
	public void onJoin(ServerSideConnectionEvent.Join event) {
		Location<?, ?> loc = event.player().location();

		JoinEventBuilder builder = new JoinEventBuilder(webhookLogger)
				.audience(event.player())
				.message(event.message())
				.location(loc.blockX(), loc.blockY(), loc.blockZ())
				.address(event.connection().address());
		queue(builder);
	}
}
