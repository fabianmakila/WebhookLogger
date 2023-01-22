package fi.fabianadrian.webhookchatlogger.client;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface Client {

    void sendMessage(Player author, Component message);
}
