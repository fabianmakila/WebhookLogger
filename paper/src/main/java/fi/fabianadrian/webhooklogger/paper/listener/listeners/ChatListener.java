package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.paper.platform.PaperPlayer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class ChatListener extends AbstractListener implements Listener {
	public ChatListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public EventType type() {
		return EventType.CHAT;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChat(AsyncChatEvent event) {
		if (super.webhooks.isEmpty()) {
			return;
		}
		ChatEventConfig config = super.webhookLogger.eventsConfig().chat();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		PaperPlayer player = new PaperPlayer(event.getPlayer());
		TagResolver.Builder builder = TagResolver.builder().resolvers(
				super.placeholderFactory.player(player),
				super.placeholderFactory.cancelled(event.isCancelled()),
				super.placeholderFactory.message(event.message())
		);

		queue(config.format(), player, builder);
	}
}
