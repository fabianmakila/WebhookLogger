package fi.fabianadrian.webhookchatlogger.common.client;

import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.dependency.Dependency;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientManager {
	private final WebhookChatLogger wcl;
	private final MiniMessage miniMessage = MiniMessage.miniMessage();
	private final DiscordClient discordClient;
	private String messageFormat;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	public ClientManager(WebhookChatLogger wcl) {
		this.wcl = wcl;
		this.discordClient = new DiscordClient(wcl);
	}

	public void send(Message message) {
		Component parsedMessage = parseMessage(message);
		this.discordClient.send(parsedMessage);
	}

	public void reload() {
		this.messageFormat = this.wcl.config().messageFormat();
		this.discordClient.reload();
	}

	private Component parseMessage(Message message) {
		String authorName = message.author().getOrDefault(Identity.NAME, "unknown author");
		Component authorDisplayName = message.author().getOrDefault(Identity.DISPLAY_NAME, Component.text(authorName));

		String timestamp = this.dateFormat.format(new Date());

		TagResolver.Builder resolverBuilder = TagResolver.builder().resolvers(
				Placeholder.unparsed("author_name", authorName),
				Placeholder.component("author_display_name", authorDisplayName),
				Placeholder.component("message", message.message()),
				Placeholder.unparsed("timestamp", timestamp)
		);

		if (this.wcl.dependencyManager().isPresent(Dependency.MINI_PLACEHOLDERS)) {
			resolverBuilder = resolverBuilder.resolver(MiniPlaceholders.getAudiencePlaceholders(message.author()));
		}

		return this.miniMessage.deserialize(
				this.messageFormat,
				resolverBuilder.build()
		);
	}
}
