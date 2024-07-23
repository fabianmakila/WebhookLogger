package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class ChatEventBuilder extends EventBuilder {
	public ChatEventBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, EventType.CHAT, webhookLogger.eventsConfig().chat().format());
	}

	@Override
	public ChatEventBuilder audience(Audience audience) {
		return (ChatEventBuilder) super.audience(audience);
	}

	@Override
	public ChatEventBuilder cancelled(boolean cancelled) {
		return (ChatEventBuilder) super.cancelled(cancelled);
	}

	public ChatEventBuilder message(Component message) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.component("message", message)
		);
		return this;
	}
}
