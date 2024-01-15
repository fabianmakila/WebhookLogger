package fi.fabianadrian.webhookchatlogger.common.client;

import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.UUID;

public class ClientManager {
	private final WebhookChatLogger wcl;
	private final MiniMessage miniMessage = MiniMessage.miniMessage();
	private final DiscordClient discordClient;
	private String messageFormat;

	public ClientManager(WebhookChatLogger wcl) {
		this.wcl = wcl;

		this.discordClient = new DiscordClient(wcl);
	}

	public void log(Message message) {
		String authorName = message.author().getOrDefault(Identity.NAME, "unknown author");
		Component authorDisplayName = message.author().getOrDefault(Identity.DISPLAY_NAME, Component.text(authorName));
		UUID authorUUID = message.author().getOrDefault(Identity.UUID, UUID.fromString(authorName));

		TagResolver miniPlaceholdersResolver = MiniPlaceholders.getAudiencePlaceholders(message.author());

		Component parsedMessage = this.miniMessage.deserialize(
				this.messageFormat,
				Placeholder.unparsed("author_name", authorName),
				Placeholder.component("author_display_name", authorDisplayName),
				Placeholder.unparsed("author_uuid", authorUUID.toString()),
				miniPlaceholdersResolver
		);

		this.discordClient.send(parsedMessage);
	}

	public void reload() {
		this.messageFormat = this.wcl.config().messageFormat();

		this.discordClient.reload();
	}
}
