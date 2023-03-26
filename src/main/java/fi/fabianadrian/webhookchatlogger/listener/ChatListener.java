package fi.fabianadrian.webhookchatlogger.listener;

import fi.fabianadrian.webhookchatlogger.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.client.WebhookClient;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public final class ChatListener implements Listener {
    private final WebhookClient webhookClient;
    private boolean logCancelledMessages;

    public ChatListener(WebhookChatLogger webhookChatLogger) {
        this.webhookClient = webhookChatLogger.webhookClient();
        this.logCancelledMessages = webhookChatLogger.configManager().mainConfig().logCancelledMessages();
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!logCancelledMessages && event.isCancelled()) return;
        this.webhookClient.sendMessage(event.getPlayer(), event.message());
    }
}
