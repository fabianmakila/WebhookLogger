package fi.fabianadrian.webhooklogger.common.event;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.section.PlaceholderConfigSection;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.common.platform.Player;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PlaceholderFactory {
	private final WebhookLogger webhookLogger;
	private final PlaceholderConfigSection config;
	private final PlainTextComponentSerializer plainTextSerializer = PlainTextComponentSerializer.plainText();
	private final MiniMessage miniMessage = MiniMessage.miniMessage();

	public PlaceholderFactory(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		this.config = webhookLogger.mainConfig().placeholders();
	}

	public TagResolver timestamp() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(config.timestampFormat())
				.withZone(config.timestampTimezone());
		return Placeholder.unparsed("timestamp", formatter.format(Instant.now()));
	}

	public TagResolver cancelled(boolean cancelled) {
		String cancelledString = cancelled ? config.cancelled() : "";
		return Placeholder.unparsed("cancelled", cancelledString);
	}

	public TagResolver player(Player player) {
		List<TagResolver> resolvers = new ArrayList<>();

		resolvers.add(audience(player));

		String address = String.valueOf(player.address());
		resolvers.add(Placeholder.unparsed("address", address));

		Component location = miniMessage.deserialize(
				config.locationFormat(),
				Placeholder.unparsed("x", String.valueOf(player.location().x())),
				Placeholder.unparsed("y", String.valueOf(player.location().y())),
				Placeholder.unparsed("z", String.valueOf(player.location().z()))
		);
		resolvers.add(Placeholder.component("location", location));

		return TagResolver.builder().resolvers(resolvers).build();
	}

	public TagResolver audience(Audience audience) {
		List<TagResolver> resolvers = new ArrayList<>();

		String name = audience.getOrDefault(Identity.NAME, "unknown");
		resolvers.add(Placeholder.unparsed("name", name));

		Component displayName = audience.getOrDefault(Identity.DISPLAY_NAME, Component.text(name));
		resolvers.add(Placeholder.component("display_name", displayName));

		String uuid = audience.get(Identity.UUID).map(UUID::toString).orElse("unknown");
		resolvers.add(Placeholder.unparsed("uuid", uuid));

		if (webhookLogger.dependencyManager().isPresent(Dependency.MINI_PLACEHOLDERS)) {
			resolvers.add(MiniPlaceholders.getAudienceGlobalPlaceholders(audience));
		}

		return TagResolver.builder().resolvers(resolvers).build();
	}

	public TagResolver message(Component message) {
		if (message == null) {
			return Placeholder.unparsed("placeholder", "");
		}

		if (message instanceof TranslatableComponent) {
			message = Component.text(plainTextSerializer.serialize(message));
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
