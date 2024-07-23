package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class CommandEventBuilder extends EventBuilder {
	public CommandEventBuilder(WebhookLogger webhookLogger) {
		super(webhookLogger, EventType.COMMAND, webhookLogger.eventsConfig().command().format());
	}

	@Override
	public CommandEventBuilder cancelled(boolean cancelled) {
		return (CommandEventBuilder) super.cancelled(cancelled);
	}

	@Override
	public CommandEventBuilder audience(Audience audience) {
		return (CommandEventBuilder) super.audience(audience);
	}

	public CommandEventBuilder command(String command) {
		if (!command.startsWith("/")) {
			command = "/" + command;
		}

		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("command", command)
		);
		return this;
	}
}
