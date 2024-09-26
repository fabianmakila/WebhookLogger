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
		this.config = this.webhookLogger.mainConfig();
		parseWebhooks();

		if (this.scheduledSendMessageTask != null) {
			this.scheduledSendMessageTask.cancel(false);
		}

		this.scheduledSendMessageTask = this.webhookLogger.scheduler().scheduleAtFixedRate(
				() -> this.clients.forEach(WebhookClient::sendAll),
				0,
				this.config.sendRate(),
				TimeUnit.SECONDS
		);
	}

	private void parseWebhooks() {
		this.clients.clear();

		Logger logger = this.webhookLogger.logger();
		this.config.webhooks().forEach(webhook -> {
			if (webhook.url().isBlank()) {
				logger.warn("webhook url blank");
				// TODO Log message
				return;
			}

			WebhookClient client = new WebhookClient(logger, webhook.url());
			this.webhookLogger.listenerRegistry().registerWebhookForEvents(client, webhook.events());
			this.clients.add(client);
		});
	}
}
