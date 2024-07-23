package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class JoinQuitEventBuilder extends EventBuilder {
	public JoinQuitEventBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, EventType.JOINQUIT, webhookLogger.eventsConfig().joinQuit().format());
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
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("location", locationString)
		);

		return this;
	}

	public JoinQuitEventBuilder message(String message) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("message", message)
		);
		return this;
	}

	public JoinQuitEventBuilder address(String address) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("address", address)
		);
		return this;
	}
}
