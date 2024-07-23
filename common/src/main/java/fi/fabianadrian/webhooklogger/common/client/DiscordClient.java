package fi.fabianadrian.webhooklogger.common.client;

import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;
import org.slf4j.Logger;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class DiscordClient {
    private final Queue<String> messageQueue = new ConcurrentLinkedQueue<>();
    private final WebHookClient client;
    private final Logger logger;
    private final String url;

    public DiscordClient(Logger logger, String url) {
        this.logger = logger;
        this.url = url;

        this.client = WebHookClient.fromURL(url);
    }

    public String url() {
        return this.url;
    }

    public void add(String message) {
        this.messageQueue.add(message);
    }

    public void send() throws RuntimeException {
        // Copy messageBuffer
        List<String> messages = List.copyOf(this.messageQueue);

        // If empty don't run
        if (this.messageQueue.isEmpty()) {
            return;
        }

        // Construct webhook
        WebHook webHook = WebHook.builder().content(String.join(", ", messages)).build();
        this.client.sendWebHook(webHook).thenAccept(response -> {
            switch (response.statusCode()) {
                case 204 -> this.messageQueue.removeAll(messages);
                case 429 ->
                        this.logger.warn("Failed to send a webhook to {} due to rate limit. Consider increasing the sendRate in the configuration to avoid this", this.url);
                default ->
                        this.logger.warn("Failed to send a webhook to {}. Got status code {}", this.url, response.statusCode());
            }
        });
    }
}
