package fi.fabianadrian.webhookchatlogger.config;

import fi.fabianadrian.webhookchatlogger.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.config.client.DiscordClientConfig;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;

public final class ConfigManager {
    private final ConfigLoader<WebhookChatLoggerConfig> mainConfigLoader;
    private final ConfigLoader<DiscordClientConfig> discordConfigLoader;

    private WebhookChatLoggerConfig mainConfig;
    private DiscordClientConfig discordConfig;

    public ConfigManager(WebhookChatLogger webhookChatLogger) {
        Path dataFolder = webhookChatLogger.getDataFolder().toPath();
        this.mainConfigLoader = new ConfigLoader<>(
                WebhookChatLoggerConfig.class,
                dataFolder.resolve("main.conf"),
                options -> options.header("WebhookChatLogger Main Configuration")
        );
        this.discordConfigLoader = new ConfigLoader<>(
                DiscordClientConfig.class,
                dataFolder.resolve("discord.conf"),
                options -> options.header("WebhookChatLogger Discord Client Configuration")
        );
    }

    public void loadConfigs() {
        try {
            this.mainConfig = this.mainConfigLoader.load();
            this.mainConfigLoader.save(this.mainConfig);

            this.discordConfig = this.discordConfigLoader.load();
            this.discordConfigLoader.save(this.discordConfig);
        } catch (ConfigurateException e) {
            throw new IllegalStateException("Failed to load config", e);
        }
    }

    public WebhookChatLoggerConfig mainConfig() {
        return this.mainConfig;
    }

    public DiscordClientConfig discordConfig() {
        return this.discordConfig;
    }
}
