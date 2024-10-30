package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.Nullable;


public final class QuitEventBuilder extends EventBuilder {
	private final PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();

	public QuitEventBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, webhookLogger.eventsConfig().quit().format());
	}

	@Override
	public QuitEventBuilder cancelled(boolean cancelled) {
		return (QuitEventBuilder) super.cancelled(cancelled);
	}

	@Override
	public QuitEventBuilder audience(Audience audience) {
		return (QuitEventBuilder) super.audience(audience);
	}

	public QuitEventBuilder location(int x, int y, int z) {
		String locationString = String.format("x%s, y%s, z%s", x, y, z);
		resolvers.add(Placeholder.unparsed("location", locationString));
		return this;
	}

	public QuitEventBuilder message(@Nullable Component message) {
		String messageAsString = "";

		if (message != null) {
			messageAsString = serializer.serialize(message);
		}

		resolvers.add(Placeholder.unparsed("message", messageAsString));

		return this;
	}
}
