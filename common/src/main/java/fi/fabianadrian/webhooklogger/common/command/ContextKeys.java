package fi.fabianadrian.webhooklogger.common.command;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import org.incendo.cloud.key.CloudKey;

public final class ContextKeys {
	public static final CloudKey<WebhookLogger> WEBHOOK_LOGGER_KEY = CloudKey.of("WebhookLogger", WebhookLogger.class);

	private ContextKeys() {
	}
}
