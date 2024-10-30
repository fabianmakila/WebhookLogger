package fi.fabianadrian.webhooklogger.common.platform;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public record Location(int x, int y, int z) {
	public TagResolver tagResolver() {
		return TagResolver.resolver(
				Placeholder.unparsed("x", String.valueOf(x)),
				Placeholder.unparsed("y", String.valueOf(y)),
				Placeholder.unparsed("z", String.valueOf(z))
		);
	}
}
