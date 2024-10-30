package fi.fabianadrian.webhooklogger.common.listener;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.PlaceholderFactory;
import fi.fabianadrian.webhooklogger.common.webhook.WebhookClient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractListener {
	protected final WebhookLogger webhookLogger;
	protected final PlaceholderFactory placeholderFactory;
	private final Map<Pattern, String> replacements;
	List<WebhookClient> clients = new ArrayList<>();

	public AbstractListener(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		this.placeholderFactory = new PlaceholderFactory(webhookLogger);
		replacements = webhookLogger.mainConfig().textReplacements();
	}

	public void addWebhook(WebhookClient client) {
		clients.add(client);
	}

	//TODO Fix ugly
	protected void queue(String format, TagResolver.Builder builder) {
		TagResolver resolver = builder
				.resolver(placeholderFactory.timestamp())
				.build();

		Component component = MiniMessage.miniMessage().deserialize(format, resolver);

		String discordSerialized = DiscordSerializer.INSTANCE.serialize(component);
		for (Map.Entry<Pattern, String> entry : replacements.entrySet()) {
			Matcher matcher = entry.getKey().matcher(discordSerialized);
			discordSerialized = matcher.replaceAll(entry.getValue());
		}

		String finalDiscordSerialized = discordSerialized;
		clients.forEach(client -> client.queue(finalDiscordSerialized));
	}
}
