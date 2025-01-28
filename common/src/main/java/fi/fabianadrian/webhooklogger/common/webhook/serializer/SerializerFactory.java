package fi.fabianadrian.webhooklogger.common.webhook.serializer;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.webhook.MessageStyle;

public final class SerializerFactory {
	private final WebhookLogger webhookLogger;

	public SerializerFactory(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
	}

	public Serializer serializer(MessageStyle messageStyle) {
		switch (messageStyle) {
			case NORMAL -> {
				return new DiscordSerializer(this.webhookLogger);
			}
			case CODE_BLOCK -> {
				return new AnsiSerializer(this.webhookLogger);
			}
			default -> throw new IllegalStateException("Unknown message style");
		}
	}
}
