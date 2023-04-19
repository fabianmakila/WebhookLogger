package fi.fabianadrian.webhookchatlogger.client;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface WebhookClient {

    void sendMessage(Player author, Component message);

    void close();
}
