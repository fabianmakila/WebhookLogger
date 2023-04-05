package fi.fabianadrian.webhookchatlogger.config;

import fi.fabianadrian.webhookchatlogger.client.ClientType;
import fi.fabianadrian.webhookchatlogger.config.client.DiscordClientConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
public class WebhookChatLoggerConfig {
    @Comment("The webhook url where chat messages will be forwarded.")
    private String url = "";

    private ClientType webhookClient = ClientType.DISCORD;

    private boolean logCancelledMessages = false;

    private DiscordClientConfig discordClient = new DiscordClientConfig();

    public String url() {
        return this.url;
    }

    public ClientType webhookType() {
        return this.webhookClient;
    }

    public boolean logCancelledMessages() {
        return this.logCancelledMessages;
    }

    public DiscordClientConfig discordClientConfig() {
        return this.discordClient;
    }
}
