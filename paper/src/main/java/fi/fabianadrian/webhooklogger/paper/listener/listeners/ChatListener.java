package fi.fabianadrian.webhooklogger.paper.listener.listeners;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.event.ChatEventBuilder;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatListener extends AbstractListener<ChatEventBuilder> implements Listener {
	public ChatListener(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		ChatEventConfig config = webhookLogger.eventsConfig().chat();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		ChatEventBuilder builder = new ChatEventBuilder(webhookLogger)
				.audience(event.getPlayer())
				.cancelled(event.isCancelled())
				.message(event.message());
		queue(builder);
	}
}
