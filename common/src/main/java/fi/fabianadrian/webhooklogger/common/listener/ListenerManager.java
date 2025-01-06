package fi.fabianadrian.webhooklogger.common.listener;

import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.webhook.WebhookClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ListenerManager {
	protected Map<EventType, AbstractListener> registry = new HashMap<>();

	public abstract void registerListeners();

	public void clearRegisteredWebhooks() {
		this.registry.values().forEach(AbstractListener::clearWebhooks);
	}

	public void registerWebhookForEvents(WebhookClient client, List<EventType> events) {
		events.forEach(event -> registerWebhook(client, event));
	}

	private void registerWebhook(WebhookClient webhookClient, EventType type) {
		this.registry.get(type).registerWebhook(webhookClient);
	}
}
