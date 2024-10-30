package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;

public final class JoinEventBuilder extends EventBuilder {
	private final PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();

	public JoinEventBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, webhookLogger.eventsConfig().join().format());
	}

	@Override
	public JoinEventBuilder cancelled(boolean cancelled) {
		return (JoinEventBuilder) super.cancelled(cancelled);
	}

	@Override
	public JoinEventBuilder audience(Audience audience) {
		return (JoinEventBuilder) super.audience(audience);
	}

	public JoinEventBuilder location(int x, int y, int z) {
		String locationString = String.format("x%s, y%s, z%s", x, y, z);
		resolvers.add(Placeholder.unparsed("location", locationString));
		return this;
	}

	public JoinEventBuilder message(@Nullable Component message) {
		String messageAsString = "";

		if (message != null) {
			messageAsString = serializer.serialize(message);
		}

		resolvers.add(Placeholder.unparsed("message", messageAsString));

		return this;
	}

	public JoinEventBuilder address(InetSocketAddress address) {
		resolvers.add(Placeholder.unparsed("address", String.valueOf(address)));
		return this;
	}
}
