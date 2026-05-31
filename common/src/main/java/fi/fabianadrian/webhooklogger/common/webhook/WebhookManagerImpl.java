package fi.fabianadrian.webhooklogger.common.webhook;

import fi.fabianadrian.webhooklogger.api.WebhookManager;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public final class WebhookManagerImpl implements WebhookManager {
	private final WebhookLogger webhookLogger;
	private final List<WebhookClient> clients = new ArrayList<>();

	public WebhookManagerImpl(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	public void reload() {
		this.clients.forEach(WebhookClient::shutdown);
		this.clients.clear();
		parseWebhooks();
	}

	private void parseWebhooks() {
		this.webhookLogger.mainConfig().webhooks().forEach(webhook -> {
			if (webhook.url().isBlank()) {
				this.webhookLogger.logger().warn("You have a webhook with empty URL.");
				return;
			}

			if (webhook.events().isEmpty()) {
				this.webhookLogger.logger().warn("You have a webhook with empty events.");
				return;
			}

			WebhookClient client = new WebhookClient(this.webhookLogger, webhook);
			this.clients.add(client);
		});
	}

	@Override
	public void post(Key key, Component component) {
		this.clients.forEach(client -> client.post(key, component));
	}
}
