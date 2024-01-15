package fi.fabianadrian.webhookchatlogger.common.client;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;
import net.kyori.adventure.text.Component;

import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DiscordClient implements WebhookClient {
	private final List<Message> messageBuffer = new LinkedList<>();
	private final WebhookChatLogger wcl;
	private WebHookClient client;
	private DiscordConfigSection config;
	private ScheduledFuture<?> scheduledSendMessageTask;

	public DiscordClient(WebhookChatLogger wcl) {
		this.wcl = wcl;
	}

	@Override
	public void log(Message message) {
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
			List<Message> messages = this.messageBuffer;

			if (this.messageBuffer.isEmpty()) {
				return;
			}

			String messageFormat = this.config.messageFormat();
			StringJoiner joiner = new StringJoiner("\n");

			this.messageBuffer.forEach(message -> {
				String formattedMessage = messageFormat
						.replaceAll("<author>", message.authorName())
						.replaceAll("<message>", serializeComponent(message.content()));
				joiner.add(formattedMessage);
			});

			WebHook webHook = WebHook.builder().content(joiner.toString()).build();
			CompletableFuture<HttpResponse<String>> future = this.client.sendWebHook(webHook);

			future.thenAccept(response -> this.messageBuffer.removeAll(messages)).exceptionally(ex -> {
				this.wcl.logger().warn("Error sending webhook: " + ex.getMessage());
				return null;
			});
		};
	}

	private String serializeComponent(Component component) {
		String serialized = DiscordSerializer.INSTANCE.serialize(component);
		serialized = serialized.replaceAll("@", "(at)");
		return serialized;
	}
}
