package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookChatLogger;
import fi.fabianadrian.webhooklogger.common.config.event.ChatEventConfig;
import fi.fabianadrian.webhooklogger.common.event.ChatEventComponentBuilder;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatListener implements Listener {
	private final WebhookChatLogger wcl;

	public ChatListener(WebhookLoggerPaper plugin) {
		this.wcl = plugin.wcl();
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		ChatEventConfig config = this.wcl.eventsConfig().chat();

		if (!config.logCancelled() && event.isCancelled()) {
			return;
		}

		Component message = new ChatEventComponentBuilder(this.wcl)
				.audience(event.getPlayer())
				.cancelled(event.isCancelled())
				.message(event.message())
				.build();
		this.wcl.clientManager().send(message);
	}
}
