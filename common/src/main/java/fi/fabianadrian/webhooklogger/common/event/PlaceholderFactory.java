package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.common.platform.PlatformPlayer;
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

public final class PlaceholderFactory {
	private final WebhookLogger webhookLogger;
	private final MainConfig.PlaceholderConfig config;
	private final MiniMessage miniMessage = MiniMessage.miniMessage();

	public PlaceholderFactory(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		this.config = webhookLogger.mainConfig().placeholders();
	}

	public TagResolver timestamp() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(this.config.timestampFormat())
				.withZone(this.config.timestampTimezone());
		return Placeholder.unparsed("timestamp", formatter.format(Instant.now()));
	}

	public TagResolver cancelled(boolean cancelled) {
		String cancelledString = cancelled ? this.config.cancelled() : "";
		return Placeholder.unparsed("cancelled", cancelledString);
	}

	public TagResolver player(PlatformPlayer player) {
		List<TagResolver> resolvers = new ArrayList<>();

		resolvers.add(audience(player));

		String address = String.valueOf(player.address());
		resolvers.add(Placeholder.unparsed("address", address));

		Component location = this.miniMessage.deserialize(
				this.config.locationFormat(),
				player.location().tagResolver()
		);
		resolvers.add(Placeholder.component("location", location));

		return TagResolver.resolver(resolvers);
	}

	public TagResolver audience(Audience audience) {
		List<TagResolver> resolvers = new ArrayList<>();

		String name = audience.getOrDefault(Identity.NAME, "unknown");
		resolvers.add(Placeholder.unparsed("name", name));

		Component displayName = audience.getOrDefault(Identity.DISPLAY_NAME, Component.text(name));
		resolvers.add(Placeholder.component("display_name", displayName));

		String uuid = audience.get(Identity.UUID).map(UUID::toString).orElse("unknown");
		resolvers.add(Placeholder.unparsed("uuid", uuid));

		if (this.webhookLogger.dependencyManager().isPresent(Dependency.MINI_PLACEHOLDERS)) {
			resolvers.add(MiniPlaceholders.audienceGlobalPlaceholders());
		}

		return TagResolver.resolver(resolvers);
	}

	public TagResolver message(Component message) {
		if (message == null) {
			return Placeholder.unparsed("message", "");
		}

		return Placeholder.component("message", message);
	}

	public TagResolver command(String command) {
		if (!command.startsWith("/")) {
			command = "/" + command;
		}
		return Placeholder.unparsed("command", command);
	}
}
