package fi.fabianadrian.webhookchatlogger.sponge.listener;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.loggable.LoggableMessage;
import fi.fabianadrian.webhookchatlogger.sponge.WebhookChatLoggerSponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.PlayerChatEvent;

import java.util.Optional;

public class ChatListener {
	private final WebhookChatLogger wcl;

	public ChatListener(WebhookChatLoggerSponge plugin) {
		this.wcl = plugin.wcl();
	}

	@Listener
	public void onChat(PlayerChatEvent event) {
		Optional<ServerPlayer> playerOptional = event.player();
		if (playerOptional.isEmpty()) {
			return;
		}

		LoggableMessage message = new LoggableMessage(playerOptional.get(), event.message(), false);
		this.wcl.clientManager().send(message);
	}
}
