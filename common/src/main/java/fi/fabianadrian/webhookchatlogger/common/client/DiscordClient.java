package fi.fabianadrian.webhookchatlogger.common.client;

import club.minnced.discord.webhook.exception.HttpException;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.WebhookChatLoggerConfig;

import java.util.concurrent.TimeUnit;

public class DiscordClient implements WebhookClient {
    private final club.minnced.discord.webhook.WebhookClient client;
    private final WebhookChatLoggerConfig.DiscordClientConfig config;
    private final DiscordSerializer serializer;
    private long lastErrorTimestamp;

    public DiscordClient(WebhookChatLogger wcl, String webhookUrl) {
        this.client = club.minnced.discord.webhook.WebhookClient.withUrl(webhookUrl);
        this.client.setErrorHandler((client, message, throwable) -> {
            // Log every non http exception
            if (!(throwable instanceof HttpException ex)) {
                wcl.logger().error("Encountered an error: ", throwable);
                return;
            }

            long currentTime = System.currentTimeMillis();
            if ((currentTime - this.lastErrorTimestamp) < TimeUnit.SECONDS.toMillis(10)) {
                return;
            }

            this.lastErrorTimestamp = currentTime;
            if (ex.getCode() == 429) {
                wcl.logger().warn("Discord's webhook rate limit reached. Message sending will be delayed.");
            } else {
                wcl.logger().warn("Unhandled HttpException: ", ex);
            }
        });

        this.serializer = new DiscordSerializer();

        this.config = wcl.config().discordClientConfig();
    }

    @Override
    public void log(Message message) {
        if (this.client.isShutdown()) {
            return;
        }

        String serializedMessage = this.serializer.serialize(message.content());

        switch (this.config.messageStyle()) {
            case EMBED_PRETTY -> {
                String iconUrl = "https://crafthead.net/avatar/" + message.authorUUID();
                String nameMcUrl = "https://namemc.com/profile/" + message.authorUUID();
                WebhookEmbed embed = new WebhookEmbedBuilder()
                    .setAuthor(new WebhookEmbed.EmbedAuthor(message.authorName(), iconUrl, nameMcUrl))
                    .setDescription(serializedMessage).build();
                this.client.send(embed);
            }
            case EMBED_COMPACT -> {
                String description = String.format(this.config.messageFormat(), message.authorName(), serializedMessage);
                WebhookEmbed embed = new WebhookEmbedBuilder().setDescription(description).build();
                this.client.send(embed);
            }
            case MESSAGE ->
                this.client.send(String.format(this.config.messageFormat(), message.authorName(), serializedMessage));
            default -> throw new IllegalStateException("Unknown embed style!");
        }
    }

    @Override
    public void close() {
        this.client.close();
    }
}
