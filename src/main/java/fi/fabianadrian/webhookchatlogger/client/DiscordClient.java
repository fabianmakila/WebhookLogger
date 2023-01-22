package fi.fabianadrian.webhookchatlogger.client.discord;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import fi.fabianadrian.webhookchatlogger.client.WebhookClient;
import fi.fabianadrian.webhookchatlogger.config.client.DiscordClientConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;

public class DiscordClient implements WebhookClient {
    private final club.minnced.discord.webhook.WebhookClient client;
    private final DiscordClientConfig config;

    public DiscordClient(String webhookUrl, DiscordClientConfig config) {
        this.client = club.minnced.discord.webhook.WebhookClient.withUrl(webhookUrl);
        this.config = config;
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
