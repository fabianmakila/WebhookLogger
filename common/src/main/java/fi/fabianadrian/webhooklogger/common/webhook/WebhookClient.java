package fi.fabianadrian.webhooklogger.common.webhook;

import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;
import org.slf4j.Logger;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class WebhookClient {
	private final Queue<String> messageQueue = new ConcurrentLinkedQueue<>();
	private final WebHookClient client;
	private final Logger logger;
	private final String url;

	public WebhookClient(Logger logger, String url) {
		this.logger = logger;
		this.url = url;

		client = WebHookClient.fromURL(url);
	}

	public void queue(String message) {
		messageQueue.add(message);
	}

	public void sendAll() {
		// Copy messageBuffer
		List<String> messages = List.copyOf(messageQueue);

		// If empty don't run
		if (messageQueue.isEmpty()) {
			return;
		}

		// Construct webhook
		WebHook webHook = WebHook.builder().content(String.join("\n", messages)).build();
		client.sendWebHook(webHook).thenAccept(response -> {
			switch (response.statusCode()) {
				case 204 -> messageQueue.removeAll(messages);
				case 429 ->
						logger.warn("Failed to send a webhook to {} due to rate limit. Consider increasing the sendRate in the configuration to avoid this", url);
				default ->
						logger.warn("Failed to send a webhook to {}. Got status code {}", url, response.statusCode());
			}
		});
	}
}
