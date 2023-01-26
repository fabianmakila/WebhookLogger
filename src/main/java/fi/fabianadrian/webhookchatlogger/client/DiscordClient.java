package fi.fabianadrian.webhookchatlogger.client;

import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.exception.HttpException;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import fi.fabianadrian.webhookchatlogger.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.client.WebhookClient;
import fi.fabianadrian.webhookchatlogger.config.client.DiscordClientConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class DiscordClient implements WebhookClient {
    private final club.minnced.discord.webhook.WebhookClient client;
    private final DiscordClientConfig config;
    private long lastErrorTimestamp;

    public DiscordClient(WebhookChatLogger plugin, String webhookUrl) {
        this.client = club.minnced.discord.webhook.WebhookClient.withUrl(webhookUrl);
        this.client.setErrorHandler((client, message, throwable) -> {
            // Log every non http exception
            if (!(throwable instanceof HttpException ex)) {
                plugin.getSLF4JLogger().error("Encountered an error: ", throwable);
                return;
            }

            long currentTime = System.currentTimeMillis();
            if ((currentTime - this.lastErrorTimestamp) < TimeUnit.SECONDS.toMillis(10)) {
                return;
            }

            this.lastErrorTimestamp = currentTime;
            switch (ex.getCode()) {
                case 429 -> plugin.getSLF4JLogger().warn("Discord's webhook rate limit reached. Message sending will be delayed.");
                default -> plugin.getSLF4JLogger().warn("Unhandled HttpException: ", ex);
            }
        });

        this.config = plugin.configManager().discordConfig();
    }

    @Override
    public void sendMessage(Player author, Component message) {
        String messageAsPlainText = PlainTextComponentSerializer.plainText().serialize(message);

        WebhookEmbed embed;
        switch (this.config.embedStyle()) {
            case PRETTY -> {
                String iconUrl = "https://crafthead.net/avatar/" + author.getUniqueId();
                String nameMcUrl = "https://namemc.com/profile/" + author.getUniqueId();
                embed = new WebhookEmbedBuilder()
                        .setAuthor(new WebhookEmbed.EmbedAuthor(author.getName(), iconUrl, nameMcUrl))
                        .setDescription(messageAsPlainText).build();
            }
            case COMPACT -> {
                String description = String.format("**%s > ** %s", author.getName(), messageAsPlainText);
                embed = new WebhookEmbedBuilder().setDescription(description).build();
            }
            default -> throw new IllegalStateException("Unknown embed style!");
        }
        this.client.send(embed);
    }
}
