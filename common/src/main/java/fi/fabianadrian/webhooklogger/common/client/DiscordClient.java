package fi.fabianadrian.webhooklogger.common.client;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.section.DiscordConfigSection;
import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;
import net.kyori.adventure.text.Component;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class DiscordClient implements WebhookClient {
	private final Queue<Component> messageBuffer = new ConcurrentLinkedQueue<>();
	private final WebhookLogger webhookLogger;
	private WebHookClient client;
	private DiscordConfigSection config;
	private ScheduledFuture<?> scheduledSendMessageTask;

	public DiscordClient(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	@Override
	public void send(Component message) {
		if (this.scheduledSendMessageTask == null) {
			return;
		}

		this.messageBuffer.add(message);
	}

	@Override
	public void reload() {
		if (this.scheduledSendMessageTask != null) {
			this.scheduledSendMessageTask.cancel(false);
		}

		this.config = this.webhookLogger.mainConfig().discord();
		if (this.config.url().isBlank()) {
			return;
		}

		this.client = WebHookClient.fromURL(this.config.url());
		//TODO Test here that the client actually works and only after that proceed

		Runnable sendMessageTask = sendMessageTask();
		this.scheduledSendMessageTask = this.webhookLogger.scheduler().scheduleAtFixedRate(sendMessageTask, 0, this.config.sendRate(), TimeUnit.SECONDS);
	}

	private Runnable sendMessageTask() {
		return () -> {
			// Copy messageBuffer
			List<Component> messages = List.copyOf(this.messageBuffer);

			// If empty don't run
			if (this.messageBuffer.isEmpty()) {
				return;
			}

			// Joiner adds newlines
			StringJoiner joiner = new StringJoiner("\n");
			messages.forEach(message -> joiner.add(DiscordSerializer.INSTANCE.serialize(message)));

			// Joiner -> Combined string
			String webhookContent = joiner.toString();

			// Replacements defined in config
			for (Map.Entry<String, String> entry : this.config.textReplacements().entrySet()) {
				webhookContent = webhookContent.replaceAll(entry.getKey(), entry.getValue());
			}

			// Construct webhook
			WebHook webHook = WebHook.builder().content(webhookContent).build();

			// Construct future
			CompletableFuture<HttpResponse<String>> future = this.client.sendWebHook(webHook);

			future.thenAccept(response -> this.messageBuffer.removeAll(messages)).exceptionally(ex -> {
				this.webhookLogger.logger().warn("Error sending webhook: {}", ex.getMessage());
				return null;
			});
		};
	}
}
