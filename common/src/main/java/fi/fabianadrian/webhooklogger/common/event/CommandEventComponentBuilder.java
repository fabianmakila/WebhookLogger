package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class CommandEventComponentBuilder extends EventComponentBuilder {
	public CommandEventComponentBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, webhookLogger.eventsConfig().command().format());
	}

	@Override
	public CommandEventComponentBuilder cancelled(boolean cancelled) {
		return (CommandEventComponentBuilder) super.cancelled(cancelled);
	}

	@Override
	public CommandEventComponentBuilder audience(Audience audience) {
		return (CommandEventComponentBuilder) super.audience(audience);
	}

	public CommandEventComponentBuilder command(String command) {
		if (!command.startsWith("/")) {
			command = "/" + command;
		}

		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("command", command)
		);
		return this;
	}
}
