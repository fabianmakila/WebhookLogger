package fi.fabianadrian.webhooklogger.sponge.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.message.PlayerChatEvent;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

public final class ChatListener extends AbstractListener {

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

		SpongePlayer player = new SpongePlayer(playerOptional.get());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				placeholderFactory.player(player),
				placeholderFactory.cancelled(event.isCancelled()),
				placeholderFactory.message(event.message())
		);

		queue(config.format(), builder);
	}
}
