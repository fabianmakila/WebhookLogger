package fi.fabianadrian.webhooklogger.common.webhook.serializer;

import fi.fabianadrian.webhooklogger.common.webhook.MessageStyle;

public final class SerializerFactory {
	public Serializer serializer(MessageStyle messageStyle) {
		switch (messageStyle) {
			case DEFAULT -> {
				return new DiscordSerializer();
			}
			case ANSI -> {
				return new AnsiSerializer();
			}
			default -> throw new IllegalStateException("Unknown message style");
		}
	}
}
