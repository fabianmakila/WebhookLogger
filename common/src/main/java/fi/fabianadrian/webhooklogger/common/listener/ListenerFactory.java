package fi.fabianadrian.webhooklogger.common.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.EventType;

public abstract class ListenerFactory {
	protected final WebhookLogger webhookLogger;

	public ListenerFactory(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	protected abstract AbstractListener create(EventType eventType);
}
