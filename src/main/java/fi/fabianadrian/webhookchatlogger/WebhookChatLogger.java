package fi.fabianadrian.webhookchatlogger;

import fi.fabianadrian.webhookchatlogger.client.DiscordClient;
import fi.fabianadrian.webhookchatlogger.client.WebhookClient;
import fi.fabianadrian.webhookchatlogger.command.RootCommandExecutor;
import fi.fabianadrian.webhookchatlogger.config.ConfigManager;
import fi.fabianadrian.webhookchatlogger.listener.ChatListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class WebhookChatLogger extends JavaPlugin {
    private ConfigManager configManager;
    private WebhookClient webhookClient;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.configManager.loadConfigs();

        initializeWebhook();

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        if (this.webhookClient != null) {
            this.webhookClient.close();
        }
    }

    public ConfigManager configManager() {
        return this.configManager;
    }

    public WebhookClient webhookClient() {
        return this.webhookClient;
    }

    public void reload() {
        this.configManager.loadConfigs();
        initializeWebhook();
    }

    private void initializeWebhook() {
        String webhookUrl = this.configManager.mainConfig().url();
        if (webhookUrl.isBlank()) {
            this.getLogger().warning("Webhook url is not configured yet! Configure the url and reload the plugin using /wcl reload.");
            return;
        }

        if (this.webhookClient != null) {
            this.webhookClient.close();
        }

        this.webhookClient = new DiscordClient(this, webhookUrl);
    }

    private void registerCommands() {
        PluginCommand rootCommand = getCommand("webhookchatlogger");
        if (rootCommand == null) {
            return;
        }

        RootCommandExecutor rootCommandExecutor = new RootCommandExecutor(this);
        rootCommand.setExecutor(rootCommandExecutor);
        rootCommand.setTabCompleter(rootCommandExecutor);
    }

    private void registerListeners() {
        PluginManager manager = getServer().getPluginManager();
        Stream.of(
                new ChatListener(this)
        ).forEach(listener -> manager.registerEvents(listener, this));
    }
}
