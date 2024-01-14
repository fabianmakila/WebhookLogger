package fi.fabianadrian.webhookchatlogger.common.client.discord;

import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.client.WebhookClient;
import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import io.github._4drian3d.jdwebhooks.WebHook;
import io.github._4drian3d.jdwebhooks.WebHookClient;

import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.*;

public class DiscordClient implements WebhookClient {
	private final List<Message> messageBuffer = new LinkedList<>();
	private final StringJoiner joiner = new StringJoiner("\n");
	private final WebhookChatLogger wcl;
	private WebHookClient client;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private DiscordConfigSection config;
	private final Runnable task;
	private boolean enabled = false;

	public DiscordClient(WebhookChatLogger wcl) {
		this.wcl = wcl;
		this.task = () -> {
			List<Message> messages = this.messageBuffer;

			if (this.messageBuffer.isEmpty()) {
				return;
			}

			String messageFormat = this.config.messageFormat();
			this.messageBuffer.forEach(message -> {
				String formattedMessage = String.format(messageFormat, message.authorName(), message.content());
				this.joiner.add(formattedMessage);
			});

			WebHook webHook = WebHook.builder().content(this.joiner.toString()).build();
			CompletableFuture<HttpResponse<String>> future = this.client.sendWebHook(webHook);

			future.thenAccept(response -> this.messageBuffer.removeAll(messages)).exceptionally(ex -> {
				this.wcl.logger().warn("Error sending webhook: " + ex.getMessage());
				return null;
			});
		};
	}

	@Override
	public void log(Message message) {
		if (!this.enabled) {
			return;
		}

		this.messageBuffer.add(message);
	}

	@Override
	public void reload() {
		shutdown();
		this.config = this.wcl.config().discordSection();
		if (config.id().isBlank() || config.token().isBlank()) {
			return;
		}

		this.client = WebHookClient.from(config.id(), config.token());
		//TODO Test here that the client actually works and only after that proceed

		this.scheduler.scheduleAtFixedRate(task, 0, config.sendRate(), TimeUnit.SECONDS);
		this.enabled = true;
	}

	@Override
	public void shutdown() {
		this.enabled = false;

		this.scheduler.shutdown();
		this.client = null;
		this.config = null;
	}
}
