package fi.fabianadrian.webhookchatlogger.common;

import fi.fabianadrian.webhookchatlogger.common.client.DiscordClient;
import fi.fabianadrian.webhookchatlogger.common.client.WebhookClient;
import fi.fabianadrian.webhookchatlogger.common.config.ConfigManager;
import fi.fabianadrian.webhookchatlogger.common.config.WebhookChatLoggerConfig;
import org.slf4j.Logger;

import java.nio.file.Path;

public class WebhookChatLogger {
    private final Logger logger;
    private final ConfigManager<WebhookChatLoggerConfig> configManager;
    private WebhookClient webhookClient;

    public WebhookChatLogger(Logger logger, Path dataFolder) {
        this.logger = logger;
        this.configManager = ConfigManager.create(
            dataFolder,
            "config.yml",
            WebhookChatLoggerConfig.class,
            logger
        );
        this.configManager.reload();

        initializeWebhook();
    }

    private void initializeWebhook() {
        String webhookUrl = this.config().url();
        if (webhookUrl.isBlank()) {
            this.logger.warn("Webhook url is not configured yet! Configure the url and reload the plugin using /wcl reload.");
            return;
        }

        if (this.webhookClient != null) {
            this.webhookClient.close();
        }

        this.webhookClient = new DiscordClient(this, webhookUrl);
    }

    public WebhookClient webhookClient() {
        return this.webhookClient;
    }

    public void reload() {
        this.configManager.reload();
        initializeWebhook();
    }

    public void shutdown() {
        if (this.webhookClient != null) {
            this.webhookClient.close();
        }
    }

    public WebhookChatLoggerConfig config() {
        return this.configManager.config();
    }

    public Logger logger() {
        return this.logger;
    }
}
