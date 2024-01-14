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
	private final String messageFormat;
	private final WebhookChatLogger wcl;
	private final WebHookClient client;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	public DiscordClient(WebhookChatLogger wcl) {
		this.wcl = wcl;

		final DiscordConfigSection config = wcl.config().discordSection();
		this.client = WebHookClient.from(config.id(), config.token());

		this.messageFormat = config.messageFormat();

		Runnable task = () -> {
			List<Message> messages = this.messageBuffer;

			this.messageBuffer.forEach(message -> {
				String formattedMessage = String.format(messageFormat, message.authorName(), message.content());
				this.joiner.add(formattedMessage);
			});

			WebHook webHook = WebHook.builder().content(joiner.toString()).build();
			CompletableFuture<HttpResponse<String>> future = this.client.sendWebHook(webHook);

			future.thenAccept(response -> messageBuffer.removeAll(messages)).exceptionally(ex -> {
				this.wcl.logger().warn("Error sending webhook: " + ex.getMessage());
				return null;
			});
		};

		this.scheduler.scheduleAtFixedRate(task, 0, config.sendRate(), TimeUnit.SECONDS);
	}

	@Override
	public void log(Message message) {
		this.messageBuffer.add(message);
	}

	@Override
	public void close() {
		this.scheduler.shutdown();
	}
}
