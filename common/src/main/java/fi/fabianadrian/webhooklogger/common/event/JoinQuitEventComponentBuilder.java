package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookChatLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public class JoinQuitEventComponentBuilder extends EventComponentBuilder {
	public JoinQuitEventComponentBuilder(WebhookChatLogger wcl) {
		super(wcl, wcl.eventsConfig().joinQuit().format());
	}

	@Override
	public JoinQuitEventComponentBuilder cancelled(boolean cancelled) {
		return (JoinQuitEventComponentBuilder) super.cancelled(cancelled);
	}

	@Override
	public JoinQuitEventComponentBuilder audience(Audience audience) {
		return (JoinQuitEventComponentBuilder) super.audience(audience);
	}

	public JoinQuitEventComponentBuilder location(int x, int y, int z) {
		String locationString = String.format("x%s, y%s, z%s", x, y, z);
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("location", locationString)
		);

		return this;
	}

	public JoinQuitEventComponentBuilder message(String message) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("message", message)
		);
		return this;
	}

	public JoinQuitEventComponentBuilder address(String address) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("address", address)
		);
		return this;
	}
}
