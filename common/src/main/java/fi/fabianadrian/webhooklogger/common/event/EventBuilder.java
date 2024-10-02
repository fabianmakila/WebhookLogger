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
import java.util.UUID;

public abstract class EventBuilder {
	protected final WebhookLogger webhookLogger;
	protected final PlaceholderConfigSection placeholderConfig;
	private final EventType eventType;
	protected TagResolver.Builder resolverBuilder;
	protected String format;

	public EventBuilder(WebhookLogger webhookLogger, EventType eventType, String format) {
		this.webhookLogger = webhookLogger;
		this.eventType = eventType;
		this.format = format;

		placeholderConfig = webhookLogger.mainConfig().placeholders();

		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(placeholderConfig.timestampFormat())
				.withZone(placeholderConfig.timestampTimezone());
		resolverBuilder = TagResolver.builder().resolvers(
				Placeholder.unparsed("timestamp", formatter.format(Instant.now()))
		);
	}

	protected EventBuilder audience(Audience audience) {
		String name = audience.getOrDefault(Identity.NAME, "unknown");
		Component displayName = audience.getOrDefault(Identity.DISPLAY_NAME, Component.text(name));

		UUID uuid = audience.getOrDefault(Identity.UUID, null);
		String uuidAsString = uuid == null ? "unknown" : uuid.toString();

		resolverBuilder = resolverBuilder.resolvers(
				Placeholder.unparsed("audience_name", name),
				Placeholder.component("audience_display_name", displayName),
				Placeholder.unparsed("audience_uuid", uuidAsString)
		);

		if (webhookLogger.dependencyManager().isPresent(Dependency.MINI_PLACEHOLDERS)) {
			resolverBuilder = resolverBuilder.resolver(MiniPlaceholders.getAudienceGlobalPlaceholders(audience));
		}

		return this;
	}

	protected EventBuilder cancelled(boolean cancelled) {
		String cancelledString = cancelled ? placeholderConfig.cancelled() : "";
		resolverBuilder = resolverBuilder.resolver(Placeholder.unparsed("cancelled", cancelledString));
		return this;
	}

	public Component component() {
		return MiniMessage.miniMessage().deserialize(
				format,
				resolverBuilder.build()
		);
	}

	public EventType type() {
		return eventType;
	}
}
