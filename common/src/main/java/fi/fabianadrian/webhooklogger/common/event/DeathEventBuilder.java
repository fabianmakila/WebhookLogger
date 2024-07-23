package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class DeathEventBuilder extends EventBuilder {
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
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("location", locationString)
		);

		return this;
	}

	public DeathEventBuilder message(String message) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("message", message)
		);
		return this;
	}
}
