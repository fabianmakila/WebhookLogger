package fi.fabianadrian.webhookchatlogger.paper.listener;

import fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPlugin;
import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.client.WebhookClient;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatListener implements Listener {
    private final WebhookChatLogger wcl;

    public ChatListener(WebhookChatLoggerPlugin plugin) {
        this.wcl = plugin.wcl();
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        final boolean logCancelledMessages = this.wcl.config().logCancelledMessages();

        if (!logCancelledMessages && event.isCancelled()) {
            return;
        }

        Message message = new Message(event.getPlayer(), event.message());
        this.wcl.clientManager().log(message);
    }
}
