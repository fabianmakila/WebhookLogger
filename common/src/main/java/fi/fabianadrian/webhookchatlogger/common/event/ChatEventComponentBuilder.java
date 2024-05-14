package fi.fabianadrian.webhookchatlogger.common.event;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class ChatEventComponentBuilder extends EventComponentBuilder {
	public ChatEventComponentBuilder(WebhookChatLogger wcl) {
		super(wcl, wcl.eventsConfig().chat().format());
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
