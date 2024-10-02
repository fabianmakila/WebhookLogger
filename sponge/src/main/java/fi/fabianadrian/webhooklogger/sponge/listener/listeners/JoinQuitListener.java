package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.JoinQuitEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.world.Location;

public final class JoinQuitListener extends AbstractListener<JoinQuitEventBuilder> {
	public JoinQuitListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Listener
	public void onJoin(ServerSideConnectionEvent.Join event) {
		Location<?, ?> loc = event.player().location();

		JoinQuitEventBuilder builder = new JoinQuitEventBuilder(webhookLogger)
				.audience(event.player())
				.message(event.message())
				.location(loc.blockX(), loc.blockY(), loc.blockZ())
				.address(event.connection().address());
		queue(builder);
	}

	@Listener
	public void onQuit(ServerSideConnectionEvent.Disconnect event) {
		Location<?, ?> loc = event.player().location();

		JoinQuitEventBuilder builder = new JoinQuitEventBuilder(webhookLogger)
				.audience(event.player())
				.message(event.message())
				.location(loc.blockX(), loc.blockY(), loc.blockZ())
				.address(event.connection().address());
		queue(builder);
	}
}
