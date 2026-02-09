package fi.fabianadrian.webhooklogger.common.event;

import net.draycia.carbon.api.event.events.CarbonChatEvent;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public final class CarbonPlaceholderFactory {
	public TagResolver carbon(CarbonChatEvent event) {
		return TagResolver.resolver(
				Placeholder.unparsed("key", event.chatChannel().key().asMinimalString()),
				Placeholder.unparsed("name", event.sender().username()),
				Placeholder.component("displayname", event.sender().displayName()),
				Placeholder.component("message", event.message()),
				Placeholder.component("original_message", event.originalMessage())
		);
	}
}
