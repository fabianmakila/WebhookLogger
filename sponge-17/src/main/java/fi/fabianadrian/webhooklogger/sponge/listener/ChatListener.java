package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.sponge.platform.SpongePlayer;
import net.kyori.adventure.key.Key;
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

	@Override
	public Key key() {
		return Key.key("webhooklogger", "chat");
	}

	@Listener
	@IsCancelled(Tristate.UNDEFINED)
	public void onChat(PlayerChatEvent.Submit event) {
		ChatEventConfig config = super.webhookLogger.eventsConfig().chat();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Optional<ServerPlayer> playerOptional = event.player();
		if (playerOptional.isEmpty()) {
			return;
		}

		SpongePlayer player = new SpongePlayer(playerOptional.get());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(player),
				super.placeholderFactory.cancelled(event.isCancelled()),
				super.placeholderFactory.message(event.message())
		);

		post(config.format(), player, builder);
	}
}
