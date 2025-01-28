package fi.fabianadrian.webhooklogger.common.webhook.serializer;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.ansi.ColorLevel;

public final class AnsiSerializer implements Serializer {
	private final ANSIComponentSerializer serializer;

	public AnsiSerializer(WebhookLogger webhookLogger) {
		this.serializer = ANSIComponentSerializer
				.builder()
				.colorLevel(ColorLevel.INDEXED_8)
				.flattener(webhookLogger.componentFlattener())
				.build();
	}

	@Override
	public String serialize(Component component) {
		return this.serializer.serialize(component);
	}
}
