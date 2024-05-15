package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class ChatEventComponentBuilder extends EventComponentBuilder {
	public ChatEventComponentBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, webhookLogger.eventsConfig().chat().format());
	}

	@Override
	public ChatEventComponentBuilder audience(Audience audience) {
		return (ChatEventComponentBuilder) super.audience(audience);
	}

	@Override
	public ChatEventComponentBuilder cancelled(boolean cancelled) {
		return (ChatEventComponentBuilder) super.cancelled(cancelled);
	}

	public ChatEventComponentBuilder message(Component message) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.component("message", message)
		);
		return this;
	}
}
