package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.section.PlaceholderConfigSection;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class EventBuilder {
	protected final WebhookLogger webhookLogger;
	protected final PlaceholderConfigSection placeholderConfig;
	protected List<TagResolver> resolvers = new ArrayList<>();
	protected String format;

	public EventBuilder(WebhookLogger webhookLogger, String format) {
		this.webhookLogger = webhookLogger;
		this.format = format;

		placeholderConfig = webhookLogger.mainConfig().placeholders();

		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(placeholderConfig.timestampFormat())
				.withZone(placeholderConfig.timestampTimezone());
		resolvers.add(Placeholder.unparsed("timestamp", formatter.format(Instant.now())));
	}

	protected EventBuilder audience(Audience audience) {
		String name = audience.getOrDefault(Identity.NAME, "unknown");
		Component displayName = audience.getOrDefault(Identity.DISPLAY_NAME, Component.text(name));

		UUID uuid = audience.getOrDefault(Identity.UUID, null);
		String uuidAsString = uuid == null ? "unknown" : uuid.toString();

		resolvers.add(Placeholder.unparsed("audience_name", name));
		resolvers.add(Placeholder.component("audience_display_name", displayName));
		resolvers.add(Placeholder.unparsed("audience_uuid", uuidAsString));

		if (webhookLogger.dependencyManager().isPresent(Dependency.MINI_PLACEHOLDERS)) {
			resolvers.add(MiniPlaceholders.getAudienceGlobalPlaceholders(audience));
		}

		return this;
	}

	protected EventBuilder cancelled(boolean cancelled) {
		String cancelledString = cancelled ? placeholderConfig.cancelled() : "";
		resolvers.add(Placeholder.unparsed("cancelled", cancelledString));
		return this;
	}

	public Component component() {
		TagResolver resolver = TagResolver.builder().resolvers(resolvers).build();
		return MiniMessage.miniMessage().deserialize(
				format,
				resolver
		);
	}
}
