package fi.fabianadrian.webhooklogger.common.webhook;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class WebhookManager {
	private final WebhookLogger webhookLogger;
	private final List<WebhookClient> clients = new ArrayList<>();
	private ScheduledFuture<?> scheduledSendMessageTask;
	private MainConfig config;

	public WebhookManager(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	public void reload() {
		if (scheduledSendMessageTask != null) {
			scheduledSendMessageTask.cancel(false);
		}

		config = webhookLogger.mainConfig();

		this.clients.clear();
		parseWebhooks();

		if (clients.isEmpty()) {
			return;
		}

		scheduledSendMessageTask = webhookLogger.scheduler().scheduleAtFixedRate(
				() -> clients.forEach(WebhookClient::sendAll),
				0,
				config.sendRate(),
				TimeUnit.SECONDS
		);
	}

	private void parseWebhooks() {
		Logger logger = webhookLogger.logger();

		config.webhooks().forEach(webhook -> {
			if (webhook.url().isBlank()) {
				logger.warn("You have a webhook with empty URL.");
				return;
			}

			if (webhook.events().isEmpty()) {
				logger.warn("You have a webhook with empty events.");
				return;
			}

			WebhookClient client = new WebhookClient(this.webhookLogger, webhook.url());
			clients.add(client);
			webhookLogger.listenerManager().registerWebhookForEvents(client, webhook.events());
		});
	}
}
