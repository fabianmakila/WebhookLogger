package fi.fabianadrian.webhooklogger.common.webhook.serializer;

import dev.vankka.mcdiscordreserializer.discord.DiscordSerializerOptions;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.text.Component;

public final class DiscordSerializer implements Serializer {
	private final dev.vankka.mcdiscordreserializer.discord.DiscordSerializer serializer;

	public DiscordSerializer(WebhookLogger webhookLogger) {
		this.serializer = new dev.vankka.mcdiscordreserializer.discord.DiscordSerializer();
		this.serializer.setDefaultOptions(DiscordSerializerOptions.defaults().withFlattener(webhookLogger.componentFlattener()));
	}

	@Override
	public String serialize(Component component) {
		return this.serializer.serialize(component);
	}
}
