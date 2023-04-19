package fi.fabianadrian.webhookchatlogger.command;

import fi.fabianadrian.webhookchatlogger.WebhookChatLogger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class WebhookChatLoggerCommand implements CommandExecutor {

    private final WebhookChatLogger plugin;

    public WebhookChatLoggerCommand(WebhookChatLogger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        if ("reload".equalsIgnoreCase(args[0])) {
            this.plugin.reload();
            sender.sendMessage(Component.text("Reload complete!", NamedTextColor.GREEN));
            return true;
        }

        return false;
    }
}
