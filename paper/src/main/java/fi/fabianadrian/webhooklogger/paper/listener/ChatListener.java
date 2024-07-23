package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.event.ChatEventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatListener implements Listener {
	private final WebhookLogger webhookLogger;

	public ChatListener(WebhookLoggerPaper plugin) {
		this.webhookLogger = plugin.webhookLogger();
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		ChatEventConfig config = this.webhookLogger.eventsConfig().chat();

		if (!config.enabled() || !config.logCancelled() && event.isCancelled()) {
			return;
		}

		EventBuilder builder = new ChatEventBuilder(this.webhookLogger)
				.audience(event.getPlayer())
				.cancelled(event.isCancelled())
				.message(event.message());
		this.webhookLogger.clientManager().send(builder);
	}
}
