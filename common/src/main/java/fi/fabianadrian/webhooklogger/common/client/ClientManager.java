package fi.fabianadrian.webhooklogger.common.client;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class ClientManager {
	private final WebhookLogger webhookLogger;
	private final Map<EventType, DiscordClient> clients = new HashMap<>();
	private DiscordClient defaultClient;
	private ScheduledFuture<?> scheduledSendMessageTask;
	private MainConfig config;

	public ClientManager(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	public void send(EventBuilder eventBuilder) {
		DiscordClient client = this.clients.getOrDefault(eventBuilder.type(), this.defaultClient);

		if (client == null) {
			return;
		}

		String discordSerialized = DiscordSerializer.INSTANCE.serialize(eventBuilder.component());
		for (Map.Entry<String, String> entry : this.config.textReplacements().entrySet()) {
			discordSerialized = discordSerialized.replaceAll(entry.getKey(), entry.getValue());
		}

		client.add(discordSerialized);
	}

	public void reload() {
		this.config = this.webhookLogger.mainConfig();

		parseWebhooks();

		if (this.scheduledSendMessageTask != null) {
			this.scheduledSendMessageTask.cancel(false);
		}

		this.scheduledSendMessageTask = this.webhookLogger.scheduler().scheduleAtFixedRate(
				() -> {
					this.defaultClient.send();
					this.clients.values().forEach(DiscordClient::send);
				},
				0,
				this.config.sendRate(),
				TimeUnit.SECONDS
		);
	}

	private void parseWebhooks() {
		this.defaultClient = null;
		this.clients.clear();

		this.config.webhooks().forEach((key, url) -> {
			if (url.isBlank()) {
				this.webhookLogger.logger().warn("{} webhook has empty URL! Please check the configuration.", key);
				return;
			}

			if (key.equalsIgnoreCase("default")) {
				if (this.defaultClient != null) {
					this.webhookLogger.logger().warn("Duplicate default logger found! Please check the webhook configuration.");
				}

				this.defaultClient = new DiscordClient(this.webhookLogger.logger(), url);
				return;
			}

			EventType type;
			try {
				type = EventType.valueOf(key.toUpperCase());
			} catch (IllegalStateException e) {
				this.webhookLogger.logger().warn("Unknown event {}! Please check the webhook configuration.", key);
				return;
			}

			this.clients.put(type, createClient(url));
		});
	}

	private DiscordClient createClient(String url) {
		for (DiscordClient client : this.clients.values()) {
			if (url.equals(client.url())) {
				return client;
			}
		}
		return new DiscordClient(this.webhookLogger.logger(), url);
	}
}
