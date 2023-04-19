package fi.fabianadrian.webhookchatlogger.listener;

import fi.fabianadrian.webhookchatlogger.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.client.WebhookClient;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatListener implements Listener {
    private final WebhookChatLogger plugin;

    public ChatListener(WebhookChatLogger plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        final boolean logCancelledMessages = this.plugin.configManager().mainConfig().logCancelledMessages();
        final WebhookClient client = this.plugin.webhookClient();

        if (client == null || !logCancelledMessages && event.isCancelled()) {
            return;
        }

        this.plugin.webhookClient().sendMessage(event.getPlayer(), event.message());
    }
}
