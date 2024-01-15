package fi.fabianadrian.webhookchatlogger.common.client;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;
import net.kyori.adventure.text.Component;

import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DiscordClient implements WebhookClient {
	private final List<Component> messageBuffer = new LinkedList<>();
	private final WebhookChatLogger wcl;
	private WebHookClient client;
	private DiscordConfigSection config;
	private ScheduledFuture<?> scheduledSendMessageTask;

	public DiscordClient(WebhookChatLogger wcl) {
		this.wcl = wcl;
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

		this.config = this.wcl.config().discordSection();
		if (this.config.id().isBlank() || this.config.token().isBlank()) {
			return;
		}

		this.client = WebHookClient.from(this.config.id(), this.config.token());
		//TODO Test here that the client actually works and only after that proceed

		Runnable sendMessageTask = sendMessageTask();
		this.scheduledSendMessageTask = this.wcl.scheduler().scheduleAtFixedRate(sendMessageTask, 0, this.config.sendRate(), TimeUnit.SECONDS);
	}

	private Runnable sendMessageTask() {
		return () -> {
			List<Component> messages = this.messageBuffer;

			if (this.messageBuffer.isEmpty()) {
				return;
			}

			StringJoiner joiner = new StringJoiner("\n");
			this.messageBuffer.forEach(message -> joiner.add(DiscordSerializer.INSTANCE.serialize(message)));

			String webhookContent = joiner.toString();
			for (Map.Entry<String, String> entry : this.config.textReplacements().entrySet()) {
				webhookContent = webhookContent.replaceAll(entry.getKey(), entry.getValue());
			}

			WebHook webHook = WebHook.builder().content(webhookContent).build();
			CompletableFuture<HttpResponse<String>> future = this.client.sendWebHook(webHook);

			future.thenAccept(response -> this.messageBuffer.removeAll(messages)).exceptionally(ex -> {
				this.wcl.logger().warn("Error sending webhook: " + ex.getMessage());
				return null;
			});
		};
	}
}
