package fi.fabianadrian.webhooklogger.common.client;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ClientManager {
	private final WebhookLogger webhookLogger;
	private final WebhookRegistry registry = new WebhookRegistry();
	private ScheduledFuture<?> scheduledSendMessageTask;
	private MainConfig config;

	public ClientManager(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	public void send(EventBuilder eventBuilder) {
		List<DiscordClient> clients = this.registry.forEventType(eventBuilder.type());
		if (clients.isEmpty()) {
			return;
		}

		String discordSerialized = DiscordSerializer.INSTANCE.serialize(eventBuilder.component());
		for (Map.Entry<Pattern, String> entry : this.config.textReplacements().entrySet()) {
			Matcher matcher = entry.getKey().matcher(discordSerialized);
			discordSerialized = matcher.replaceAll(entry.getValue());
		}

		//TODO Fix ugly
		String finalDiscordSerialized = discordSerialized;
		clients.forEach(client -> client.queue(finalDiscordSerialized));
	}

	public void reload() {
		this.config = this.webhookLogger.mainConfig();

		this.registry.clear();
		parseWebhooks();

		if (this.scheduledSendMessageTask != null) {
			this.scheduledSendMessageTask.cancel(false);
		}

		this.scheduledSendMessageTask = this.webhookLogger.scheduler().scheduleAtFixedRate(
				() -> this.registry.webhooks().forEach(DiscordClient::sendAll),
				0,
				this.config.sendRate(),
				TimeUnit.SECONDS
		);
	}

	public WebhookRegistry registry() {
		return this.registry;
	}

	private void parseWebhooks() {
		Logger logger = this.webhookLogger.logger();
		this.config.webhooks().forEach(webhook -> {
			if (webhook.url().isBlank()) {
				logger.warn("webhook url blank");
				// TODO Log message
				return;
			}

			this.registry.register(new DiscordClient(logger, webhook.url()), webhook.events());
		});
	}
}
