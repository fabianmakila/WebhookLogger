package fi.fabianadrian.webhookchatlogger.common.event;

import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.section.PlaceholderConfigSection;
import fi.fabianadrian.webhookchatlogger.common.dependency.Dependency;
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

public abstract class EventComponentBuilder {
	protected final WebhookChatLogger wcl;
	protected final PlaceholderConfigSection placeholderConfig;
	protected TagResolver.Builder resolverBuilder;
	protected String format;

	public EventComponentBuilder(WebhookChatLogger wcl, String format) {
		this.wcl = wcl;
		this.format = format;

		this.placeholderConfig = wcl.mainConfig().placeholders();

		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(this.placeholderConfig.timestampFormat())
				.withZone(this.placeholderConfig.timestampTimezone());
		this.resolverBuilder = TagResolver.builder().resolvers(
				Placeholder.unparsed("timestamp", formatter.format(Instant.now()))
		);
	}

	protected EventComponentBuilder audience(Audience audience) {
		String name = audience.getOrDefault(Identity.NAME, "unknown");
		Component displayName = audience.getOrDefault(Identity.DISPLAY_NAME, Component.text(name));

		UUID uuid = audience.getOrDefault(Identity.UUID, null);
		String uuidAsString = uuid == null ? "unknown" : uuid.toString();

		this.resolverBuilder = this.resolverBuilder.resolvers(
				Placeholder.unparsed("audience_name", name),
				Placeholder.component("audience_display_name", displayName),
				Placeholder.unparsed("audience_uuid", uuidAsString)
		);

		if (this.wcl.dependencyManager().isPresent(Dependency.MINI_PLACEHOLDERS)) {
			this.resolverBuilder = this.resolverBuilder.resolver(MiniPlaceholders.getAudienceGlobalPlaceholders(audience));
		}

		return this;
	}

	protected EventComponentBuilder cancelled(boolean cancelled) {
		String cancelledString = cancelled ? this.placeholderConfig.cancelled() : "";
		this.resolverBuilder = resolverBuilder.resolver(Placeholder.unparsed("cancelled", cancelledString));
		return this;
	}

	public Component build() {
		return MiniMessage.miniMessage().deserialize(
				this.format,
				this.resolverBuilder.build()
		);
	}
}