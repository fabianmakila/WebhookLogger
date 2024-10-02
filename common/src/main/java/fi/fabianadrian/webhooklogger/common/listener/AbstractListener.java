package fi.fabianadrian.webhooklogger.common.listener;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializer;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.common.webhook.WebhookClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractListener<B extends EventBuilder> {
	protected final WebhookLogger webhookLogger;
	private final Map<Pattern, String> replacements;
	List<WebhookClient> clients = new ArrayList<>();

	public AbstractListener(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		replacements = webhookLogger.mainConfig().textReplacements();
	}

	public void addWebhook(WebhookClient client) {
		clients.add(client);
	}

	protected void queue(B builder) {
		String discordSerialized = DiscordSerializer.INSTANCE.serialize(builder.component());
		for (Map.Entry<Pattern, String> entry : replacements.entrySet()) {
			Matcher matcher = entry.getKey().matcher(discordSerialized);
			discordSerialized = matcher.replaceAll(entry.getValue());
		}

		//TODO Fix ugly
		String finalDiscordSerialized = discordSerialized;
		clients.forEach(client -> client.queue(finalDiscordSerialized));
	}
}
