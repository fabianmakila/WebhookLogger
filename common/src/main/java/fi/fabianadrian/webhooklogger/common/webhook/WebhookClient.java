package fi.fabianadrian.webhooklogger.common.webhook;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import dev.vankka.mcdiscordreserializer.discord.DiscordSerializerOptions;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentEncoder;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.ansi.ColorLevel;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebhookClient {
    private final WebHookClient client;
    private final WebhookLogger webhookLogger;
    private final ScheduledFuture<?> scheduledSendMessageTask;
    private final MainConfig.WebhookConfig config;
    private final ComponentEncoder<Component, String> serializer;
    private final Queue<WebHook> webhookQueue = new ArrayDeque<>();
    private StringJoiner joiner;
    private int messagesBuffered = 0;

    public WebhookClient(WebhookLogger webhookLogger, MainConfig.WebhookConfig config) {
        this.webhookLogger = webhookLogger;
        this.config = config;
        this.client = WebHookClient.fromURL(config.url());

        switch (config.messageStyle()) {
            case NORMAL -> {
                DiscordSerializer discordSerializer = new DiscordSerializer();
                discordSerializer.setDefaultOptions(DiscordSerializerOptions.defaults().withFlattener(webhookLogger.componentFlattener()));
                this.serializer = discordSerializer;
                this.joiner = new StringJoiner("\n");
            }
            case CODE_BLOCK -> {
                this.joiner = new StringJoiner("\n", "```ansi\n", "\n```");
                this.serializer = ANSIComponentSerializer
                        .builder()
                        .colorLevel(ColorLevel.INDEXED_8)
                        .flattener(webhookLogger.componentFlattener())
                        .build();
            }
            default -> throw new IllegalStateException("Unknown message style");
        }

        this.scheduledSendMessageTask = webhookLogger.scheduler().scheduleAtFixedRate(
                () -> {
                    if (this.messagesBuffered >= this.config.minimumQueueSize()) {
                        join();
                    }
                    send();
                },
                0,
                config.sendRate(),
                TimeUnit.SECONDS
        );
    }

    public void add(Component component) {
        this.webhookLogger.scheduler().schedule(() -> {
            String serialized = this.serializer.serialize(component);

            for (Map.Entry<Pattern, String> entry : this.webhookLogger.mainConfig().textReplacements().entrySet()) {
                Matcher matcher = entry.getKey().matcher(serialized);
                serialized = matcher.replaceAll(entry.getValue());
            }

            if (this.joiner.length() + serialized.length() > 2000) {
                join();
            }

            this.joiner.add(serialized);
            this.messagesBuffered++;
        }, 0, TimeUnit.SECONDS);
    }

    public void shutdown() {
        if (this.scheduledSendMessageTask != null) {
            this.scheduledSendMessageTask.cancel(false);
        }
    }

    private void join() {
        this.webhookQueue.add(WebHook.builder().content(this.joiner.toString()).build());
        this.messagesBuffered = 0;
        switch (this.config.messageStyle()) {
            case NORMAL -> this.joiner = new StringJoiner("\n");
            case CODE_BLOCK -> this.joiner = new StringJoiner("\n", "```ansi\n", "\n```");
            default -> throw new IllegalStateException("Unknown message style");
        }
    }

    private void send() {
        WebHook webHook = this.webhookQueue.peek();
        if (webHook == null) {
            return;
        }
        this.client.sendWebHook(webHook).thenAccept(response -> {
            switch (response.statusCode()) {
                case 204 -> this.webhookQueue.poll();
                case 429 ->
                        this.webhookLogger.logger().warn("Failed to send a webhook to {} due to rate limit. Consider increasing the sendRate in the configuration to avoid this", this.config.url());
                default ->
                        this.webhookLogger.logger().warn("Failed to send a webhook to {}. Got status code {}", this.config.url(), response.statusCode());
            }
        });
    }
}
