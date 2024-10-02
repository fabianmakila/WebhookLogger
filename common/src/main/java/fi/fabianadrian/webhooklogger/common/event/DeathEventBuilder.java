package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.Nullable;

public final class DeathEventBuilder extends EventBuilder {
	private final PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();

	public DeathEventBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, EventType.DEATH, webhookLogger.eventsConfig().death().format());
	}

	@Override
	public DeathEventBuilder cancelled(boolean cancelled) {
		return (DeathEventBuilder) super.cancelled(cancelled);
	}

	@Override
	public DeathEventBuilder audience(Audience audience) {
		return (DeathEventBuilder) super.audience(audience);
	}

	public DeathEventBuilder location(int x, int y, int z) {
		String locationString = String.format("x%s, y%s, z%s", x, y, z);
		resolverBuilder = resolverBuilder.resolver(
				Placeholder.unparsed("location", locationString)
		);

		return this;
	}

	public DeathEventBuilder message(@Nullable Component message) {
		String messageAsString = "";

		if (message != null) {
			messageAsString = serializer.serialize(message);
		}

		resolverBuilder = resolverBuilder.resolver(
				Placeholder.unparsed("message", messageAsString)
		);

		return this;
	}
}
