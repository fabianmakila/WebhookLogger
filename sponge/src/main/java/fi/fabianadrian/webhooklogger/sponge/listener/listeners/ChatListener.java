package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.message.PlayerChatEvent;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

public final class ChatListener extends AbstractListener<ChatEventBuilder> {

	public ChatListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Listener
	@IsCancelled(Tristate.UNDEFINED)
	public void onChat(PlayerChatEvent.Submit event) {
		ChatEventConfig config = webhookLogger.eventsConfig().chat();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Optional<ServerPlayer> playerOptional = event.player();
		if (playerOptional.isEmpty()) {
			return;
		}

		ChatEventBuilder builder = new ChatEventBuilder(webhookLogger)
				.audience(playerOptional.get())
				.cancelled(event.isCancelled())
				.message(event.message());
		queue(builder);
	}
}
