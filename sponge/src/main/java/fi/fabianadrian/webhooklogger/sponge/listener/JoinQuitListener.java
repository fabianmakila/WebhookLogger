package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.common.event.JoinQuitEventBuilder;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.world.Location;

public final class JoinQuitListener {
	private final WebhookLogger webhookLogger;

	public JoinQuitListener(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	@Listener
	public void onJoin(ServerSideConnectionEvent.Join event) {
		Location<?, ?> loc = event.player().location();

		EventBuilder builder = new JoinQuitEventBuilder(this.webhookLogger)
				.audience(event.player())
				.message(event.message())
				.location(loc.blockX(), loc.blockY(), loc.blockZ())
				.address(event.connection().address());
		this.webhookLogger.clientManager().send(builder);
	}

	@Listener
	public void onQuit(ServerSideConnectionEvent.Disconnect event) {
		Location<?, ?> loc = event.player().location();

		EventBuilder builder = new JoinQuitEventBuilder(this.webhookLogger)
				.audience(event.player())
				.message(event.message())
				.location(loc.blockX(), loc.blockY(), loc.blockZ())
				.address(event.connection().address());
		this.webhookLogger.clientManager().send(builder);
	}
}
