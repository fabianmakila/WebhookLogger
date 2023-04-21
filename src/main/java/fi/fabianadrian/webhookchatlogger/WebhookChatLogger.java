package fi.fabianadrian.webhookchatlogger;

import fi.fabianadrian.webhookchatlogger.client.DiscordClient;
import fi.fabianadrian.webhookchatlogger.client.WebhookClient;
import fi.fabianadrian.webhookchatlogger.command.RootCommandExecutor;
import fi.fabianadrian.webhookchatlogger.config.ConfigManager;
import fi.fabianadrian.webhookchatlogger.config.WebhookChatLoggerConfig;
import fi.fabianadrian.webhookchatlogger.listener.ChatListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class WebhookChatLogger extends JavaPlugin {
    private ConfigManager<WebhookChatLoggerConfig> configManager;
    private WebhookClient webhookClient;

    @Override
    public void onEnable() {
        this.configManager = ConfigManager.create(
            getDataFolder().toPath(),
            "config.yml",
            WebhookChatLoggerConfig.class,
            getSLF4JLogger()
        );
        this.configManager.reload();

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

    public WebhookChatLoggerConfig config() {
        return this.configManager.config();
    }

    public WebhookClient webhookClient() {
        return this.webhookClient;
    }

    public void reload() {
        this.configManager.reload();
        initializeWebhook();
    }

    private void initializeWebhook() {
        String webhookUrl = this.config().url();
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
