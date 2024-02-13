package fi.fabianadrian.webhookchatlogger.common.client;

import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.config.WebhookChatLoggerConfig;
import fi.fabianadrian.webhookchatlogger.common.dependency.Dependency;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ClientManager {
	private final WebhookChatLogger wcl;
	private final MiniMessage miniMessage = MiniMessage.miniMessage();
	private final DiscordClient discordClient;
	private WebhookChatLoggerConfig config;
	private DateTimeFormatter dateTimeFormatter;

	public ClientManager(WebhookChatLogger wcl) {
		this.wcl = wcl;
		this.discordClient = new DiscordClient(wcl);
	}

	public void send(Message message) {
		Component parsedMessage = parseMessage(message);
		this.discordClient.send(parsedMessage);
	}

	public void reload() {
		this.config = this.wcl.config();
		this.discordClient.reload();

		String pattern = this.config.placeholders().timestampFormat();
		ZoneId zoneId = this.config.placeholders().timestampTimezone();
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(pattern).withZone(zoneId);
	}

	private Component parseMessage(Message message) {
		String authorName = message.author().getOrDefault(Identity.NAME, "unknown author");
		Component authorDisplayName = message.author().getOrDefault(Identity.DISPLAY_NAME, Component.text(authorName));

		String timestamp = this.dateTimeFormatter.format(Instant.now());

		String cancelled = message.cancelled() ? this.config.placeholders().cancelled() : "";

		TagResolver.Builder resolverBuilder = TagResolver.builder().resolvers(
				Placeholder.unparsed("author_name", authorName),
				Placeholder.component("author_display_name", authorDisplayName),
				Placeholder.component("message", message.message()),
				Placeholder.unparsed("cancelled", cancelled),
				Placeholder.unparsed("timestamp", timestamp)
		);

		if (this.wcl.dependencyManager().isPresent(Dependency.MINI_PLACEHOLDERS)) {
			resolverBuilder = resolverBuilder.resolver(MiniPlaceholders.getAudienceGlobalPlaceholders(message.author()));
		}

		return this.miniMessage.deserialize(
				this.config.messageFormat(),
				resolverBuilder.build()
		);
	}
}
