package fi.fabianadrian.webhookchatlogger.paper.command;

import fi.fabianadrian.webhookchatlogger.paper.WebhookChatLoggerPlugin;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RootCommandExecutor implements CommandExecutor, TabExecutor {

    private final WebhookChatLogger wcl;

    public RootCommandExecutor(WebhookChatLoggerPlugin plugin) {
        this.wcl = plugin.wcl();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return false;
        }

        if ("reload".equalsIgnoreCase(args[0])) {
            this.wcl.reload();
            sender.sendMessage(Component.text("Reload complete!", NamedTextColor.GREEN));
            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of("reload");
    }
}
