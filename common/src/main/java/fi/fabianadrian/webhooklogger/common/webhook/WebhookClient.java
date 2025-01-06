package fi.fabianadrian.webhooklogger.common.webhook;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import fi.fabianadrian.webhooklogger.common.webhook.serializer.Serializer;
import fi.fabianadrian.webhooklogger.common.webhook.serializer.SerializerFactory;
import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;
import net.kyori.adventure.text.Component;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebhookClient {
	private final Queue<String> messageQueue = new ConcurrentLinkedQueue<>();
	private final WebHookClient client;
	private final WebhookLogger webhookLogger;
	private final String url;
	private final Serializer serializer;
	private final ScheduledFuture<?> scheduledSendMessageTask;
	private MessageStyle messageStyle = MessageStyle.NORMAL;

	public WebhookClient(WebhookLogger webhookLogger, MainConfig.WebhookConfig webhookConfig) {
		this.webhookLogger = webhookLogger;
		this.url = webhookConfig.url();
		this.client = WebHookClient.fromURL(this.url);

		if (webhookConfig.messageStyle() != null) {
			this.messageStyle = webhookConfig.messageStyle();
		}
		this.serializer = new SerializerFactory().serializer(this.messageStyle);

		int sendRate = 5;
		if (webhookConfig.sendRate() != null) {
			sendRate = webhookConfig.sendRate();
		}
		this.scheduledSendMessageTask = webhookLogger.scheduler().scheduleAtFixedRate(
				this::sendAll,
				0,
				sendRate,
				TimeUnit.SECONDS
		);
	}

	public void queue(Component component) {
		String serialized = this.serializer.serialize(component);

		for (Map.Entry<Pattern, String> entry : this.webhookLogger.mainConfig().textReplacements().entrySet()) {
			Matcher matcher = entry.getKey().matcher(serialized);
			serialized = matcher.replaceAll(entry.getValue());
		}

		this.messageQueue.add(serialized);
	}

	public void shutdown() {
		if (this.scheduledSendMessageTask != null) {
			this.scheduledSendMessageTask.cancel(false);
		}
	}

	private void sendAll() {
		// Copy messageBuffer
		List<String> messages = List.copyOf(this.messageQueue);

		// If empty don't run
		if (this.messageQueue.isEmpty()) {
			return;
		}

		String webhookContent = String.join("\n", messages);
		if (this.messageStyle == MessageStyle.CODE_BLOCK) {
			webhookContent = "```ansi\n" + webhookContent + "```";
		}

		// Construct webhook
		WebHook webHook = WebHook.builder().content(webhookContent).build();
		this.client.sendWebHook(webHook).thenAccept(response -> {
			switch (response.statusCode()) {
				case 204 -> this.messageQueue.removeAll(messages);
				case 429 ->
						this.webhookLogger.logger().warn("Failed to send a webhook to {} due to rate limit. Consider increasing the sendRate in the configuration to avoid this", this.url);
				default ->
						this.webhookLogger.logger().warn("Failed to send a webhook to {}. Got status code {}", this.url, response.statusCode());
			}
		});
	}
}
