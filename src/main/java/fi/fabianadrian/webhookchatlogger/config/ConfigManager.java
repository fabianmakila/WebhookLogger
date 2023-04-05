package fi.fabianadrian.webhookchatlogger.config;

import fi.fabianadrian.webhookchatlogger.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.config.client.DiscordClientConfig;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;

public final class ConfigManager {
    private final ConfigLoader<WebhookChatLoggerConfig> mainConfigLoader;

    private WebhookChatLoggerConfig mainConfig;

    public ConfigManager(WebhookChatLogger webhookChatLogger) {
        Path dataFolder = webhookChatLogger.getDataFolder().toPath();
        this.mainConfigLoader = new ConfigLoader<>(
                WebhookChatLoggerConfig.class,
                dataFolder.resolve("main.conf"),
                options -> options.header("WebhookChatLogger Main Configuration")
        );
    }

    public void loadConfigs() {
        try {
            this.mainConfig = this.mainConfigLoader.load();
            this.mainConfigLoader.save(this.mainConfig);
        } catch (ConfigurateException e) {
            throw new IllegalStateException("Failed to load config", e);
        }
    }

    public WebhookChatLoggerConfig mainConfig() {
        return this.mainConfig;
    }
}
