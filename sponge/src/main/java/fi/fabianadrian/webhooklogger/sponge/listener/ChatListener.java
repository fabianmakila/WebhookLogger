package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookChatLogger;
import fi.fabianadrian.webhooklogger.common.event.ChatEventComponentBuilder;
import fi.fabianadrian.webhooklogger.sponge.WebhookLoggerSponge;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.PlayerChatEvent;

import java.util.Optional;

public class ChatListener {
	private final WebhookChatLogger wcl;

	public ChatListener(WebhookLoggerSponge plugin) {
		this.wcl = plugin.wcl();
	}

	@Listener
	public void onChat(PlayerChatEvent event) {
		Optional<ServerPlayer> playerOptional = event.player();
		if (playerOptional.isEmpty()) {
			return;
		}

		Component component = new ChatEventComponentBuilder(this.wcl)
				.audience(playerOptional.get())
				.message(event.message())
				.build();
		this.wcl.clientManager().send(component);
	}
}
