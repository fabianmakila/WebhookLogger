package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.event.ChatEventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.sponge.WebhookLoggerSponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.message.PlayerChatEvent;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

public final class ChatListener {
	private final WebhookLogger webhookLogger;

	public ChatListener(WebhookLoggerSponge plugin) {
		this.webhookLogger = plugin.webhookLogger();
	}

	@Listener
	@IsCancelled(Tristate.UNDEFINED)
	public void onChat(PlayerChatEvent.Submit event) {
		ChatEventConfig config = this.webhookLogger.eventsConfig().chat();

		if (!config.enabled() || !config.logCancelled() && event.isCancelled()) {
			return;
		}

		Optional<ServerPlayer> playerOptional = event.player();
		if (playerOptional.isEmpty()) {
			return;
		}

		EventBuilder builder = new ChatEventBuilder(this.webhookLogger)
				.audience(playerOptional.get())
				.cancelled(event.isCancelled())
				.message(event.message());
		this.webhookLogger.clientManager().send(builder);
	}
}
