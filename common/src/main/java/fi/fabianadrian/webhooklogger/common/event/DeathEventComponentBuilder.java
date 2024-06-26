package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class DeathEventComponentBuilder extends EventComponentBuilder{
	public DeathEventComponentBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, webhookLogger.eventsConfig().death().format());
	}

	@Override
	public DeathEventComponentBuilder cancelled(boolean cancelled) {
		return (DeathEventComponentBuilder) super.cancelled(cancelled);
	}

	@Override
	public DeathEventComponentBuilder audience(Audience audience) {
		return (DeathEventComponentBuilder) super.audience(audience);
	}

	public DeathEventComponentBuilder location(int x, int y, int z) {
		String locationString = String.format("x%s, y%s, z%s", x, y, z);
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("location", locationString)
		);

		return this;
	}

	public DeathEventComponentBuilder message(String message) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("message", message)
		);
		return this;
	}
}
