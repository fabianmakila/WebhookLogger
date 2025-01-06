package fi.fabianadrian.webhooklogger.common.webhook;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.webhook.serializer.Serializer;
import fi.fabianadrian.webhooklogger.common.webhook.serializer.SerializerFactory;
import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebhookClient {
	private final Queue<String> messageQueue = new ConcurrentLinkedQueue<>();
	private final WebHookClient client;
	private final WebhookLogger webhookLogger;
	private final String url;
	private final Serializer serializer;

	public WebhookClient(WebhookLogger webhookLogger, String url) {
		this.webhookLogger = webhookLogger;
		this.url = url;
		this.serializer = new SerializerFactory().serializer(webhookLogger.mainConfig().messageStyle());

		this.client = WebHookClient.fromURL(url);
	}

	public void queue(Component component) {
		String serialized = this.serializer.serialize(component);

		for (Map.Entry<Pattern, String> entry : this.webhookLogger.mainConfig().textReplacements().entrySet()) {
			Matcher matcher = entry.getKey().matcher(serialized);
			serialized = matcher.replaceAll(entry.getValue());
		}

		messageQueue.add(serialized);
	}

	public void sendAll() {
		// Copy messageBuffer
		List<String> messages = List.copyOf(this.messageQueue);

		// If empty don't run
		if (this.messageQueue.isEmpty()) {
			return;
		}

		String webhookContent = String.join("\n", messages);
		if (this.webhookLogger.mainConfig().messageStyle() == MessageStyle.ANSI) {
			webhookContent = "```ansi\n" + webhookContent + "```";
		}

		// Construct webhook
		WebHook webHook = WebHook.builder().content(webhookContent).build();
		this.client.sendWebHook(webHook).thenAccept(response -> {
			switch (response.statusCode()) {
				case 204 -> this.messageQueue.removeAll(messages);
				case 429 ->
						this.webhookLogger.logger().warn("Failed to send a webhook to {} due to rate limit. Consider increasing the sendRate in the configuration to avoid this", url);
				default ->
						this.webhookLogger.logger().warn("Failed to send a webhook to {}. Got status code {}", this.url, response.statusCode());
			}
		});
	}
}
