package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;

public final class JoinQuitEventBuilder extends EventBuilder {
	private final PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();

	public JoinQuitEventBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, webhookLogger.eventsConfig().joinQuit().format());
	}

	@Override
	public JoinQuitEventBuilder cancelled(boolean cancelled) {
		return (JoinQuitEventBuilder) super.cancelled(cancelled);
	}

	@Override
	public JoinQuitEventBuilder audience(Audience audience) {
		return (JoinQuitEventBuilder) super.audience(audience);
	}

	public JoinQuitEventBuilder location(int x, int y, int z) {
		String locationString = String.format("x%s, y%s, z%s", x, y, z);
		resolvers.add(Placeholder.unparsed("location", locationString));
		return this;
	}

	public JoinQuitEventBuilder message(@Nullable Component message) {
		String messageAsString = "";

		if (message != null) {
			messageAsString = serializer.serialize(message);
		}

		resolvers.add(Placeholder.unparsed("message", messageAsString));

		return this;
	}

	public JoinQuitEventBuilder address(InetSocketAddress address) {
		resolvers.add(Placeholder.unparsed("address", String.valueOf(address)));
		return this;
	}
}
