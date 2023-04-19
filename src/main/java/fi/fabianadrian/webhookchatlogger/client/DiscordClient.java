package fi.fabianadrian.webhookchatlogger.client;

import club.minnced.discord.webhook.exception.HttpException;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhookchatlogger.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.config.client.DiscordClientConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class DiscordClient implements WebhookClient {
    private final club.minnced.discord.webhook.WebhookClient client;
    private final DiscordClientConfig config;
    private long lastErrorTimestamp;
    private final DiscordSerializer serializer;

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

        this.serializer = new DiscordSerializer();

        this.config = plugin.configManager().mainConfig().discordClientConfig();
    }

    @Override
    public void sendMessage(Player author, Component message) {
        String serializedMessage = this.serializer.serialize(message);

        switch (this.config.messageStyle()) {
            case EMBED_PRETTY -> {
                String iconUrl = "https://crafthead.net/avatar/" + author.getUniqueId();
                String nameMcUrl = "https://namemc.com/profile/" + author.getUniqueId();
                WebhookEmbed embed = new WebhookEmbedBuilder()
                        .setAuthor(new WebhookEmbed.EmbedAuthor(author.getName(), iconUrl, nameMcUrl))
                        .setDescription(serializedMessage).build();
                this.client.send(embed);
            }
            case EMBED_COMPACT -> {
                String description = String.format(this.config.messageFormat(), author.getName(), serializedMessage);
                WebhookEmbed embed = new WebhookEmbedBuilder().setDescription(description).build();
                this.client.send(embed);
            }
            case MESSAGE -> this.client.send(String.format(this.config.messageFormat(), author.getName(), serializedMessage));
            default -> throw new IllegalStateException("Unknown embed style!");
        }
    }

    @Override
    public void close() {
        this.client.close();
    }
}
