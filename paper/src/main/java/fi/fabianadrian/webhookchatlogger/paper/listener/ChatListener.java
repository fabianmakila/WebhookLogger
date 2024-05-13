package fi.fabianadrian.webhookchatlogger.paper.listener;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.loggable.LoggableMessage;
import fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPaper;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatListener implements Listener {
	private final WebhookChatLogger wcl;

	public ChatListener(WebhookChatLoggerPaper plugin) {
		this.wcl = plugin.wcl();
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		if (!this.wcl.config().chat().cancelled() && event.isCancelled()) {
			return;
		}

		LoggableMessage message = new LoggableMessage(event.getPlayer(), event.message(), event.isCancelled());
		this.wcl.clientManager().send(message);
	}
}
