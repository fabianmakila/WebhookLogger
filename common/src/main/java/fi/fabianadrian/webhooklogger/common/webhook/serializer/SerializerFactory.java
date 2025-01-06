package fi.fabianadrian.webhooklogger.common.webhook.serializer;

import fi.fabianadrian.webhooklogger.common.webhook.MessageStyle;

public final class SerializerFactory {
	public Serializer serializer(MessageStyle messageStyle) {
		switch (messageStyle) {
			case NORMAL -> {
				return new DiscordSerializer();
			}
			case CODE_BLOCK -> {
				return new AnsiSerializer();
			}
			default -> throw new IllegalStateException("Unknown message style");
		}
	}
}
