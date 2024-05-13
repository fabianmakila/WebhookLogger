package fi.fabianadrian.webhookchatlogger.common.client;

import fi.fabianadrian.webhookchatlogger.common.loggable.Loggable;
import fi.fabianadrian.webhookchatlogger.common.loggable.LoggableCommand;
import fi.fabianadrian.webhookchatlogger.common.loggable.LoggableMessage;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.WebhookChatLoggerConfig;
import fi.fabianadrian.webhookchatlogger.common.dependency.Dependency;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class ClientManager {
	private final WebhookChatLogger wcl;
	private final MiniMessage miniMessage = MiniMessage.miniMessage();
	private final DiscordClient discordClient;
	private WebhookChatLoggerConfig config;
	private DateTimeFormatter dateTimeFormatter;

	public ClientManager(WebhookChatLogger wcl) {
		this.wcl = wcl;
		this.discordClient = new DiscordClient(wcl);
	}

	public void send(Loggable loggable) {
		String timestamp = this.dateTimeFormatter.format(Instant.now());
		String cancelled = loggable.cancelled() ? this.config.placeholders().cancelled() : "";

		TagResolver.Builder resolverBuilder = TagResolver.builder().resolvers(
				Placeholder.unparsed("sender_name", loggable.senderName()),
				Placeholder.component("sender_display_name", loggable.senderDisplayName()),
				Placeholder.unparsed("cancelled", cancelled),
				Placeholder.unparsed("timestamp", timestamp)
		);

		if (this.wcl.dependencyManager().isPresent(Dependency.MINI_PLACEHOLDERS)) {
			resolverBuilder = resolverBuilder.resolver(MiniPlaceholders.getAudienceGlobalPlaceholders(loggable.sender()));
		}

		Component component;
		if (loggable instanceof LoggableCommand loggableCommand) {
			resolverBuilder = resolverBuilder.resolver(Placeholder.unparsed("command", loggableCommand.command()));
			component = this.miniMessage.deserialize(this.config.command().format(), resolverBuilder.build());
		} else if (loggable instanceof LoggableMessage loggableMessage) {
			resolverBuilder = resolverBuilder.resolver(Placeholder.component("message", loggableMessage.message()));
			component = this.miniMessage.deserialize(this.config.chat().format(), resolverBuilder.build());
		} else {
			throw new IllegalStateException("Unknown loggable");
		}

		this.discordClient.send(component);
	}

	public void reload() {
		this.config = this.wcl.config();
		this.discordClient.reload();

		String pattern = this.config.placeholders().timestampFormat();
		ZoneId zoneId = this.config.placeholders().timestampTimezone();
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId);
	}
}
