package fi.fabianadrian.webhookchatlogger.common.event;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
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
		String locationString = String.format("x%o, y%o, z%o", x, y, z);
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.unparsed("location", locationString)
		);

		return this;
	}

	public JoinQuitEventComponentBuilder message(Component message) {
		this.resolverBuilder = this.resolverBuilder.resolver(
				Placeholder.component("message", message)
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
