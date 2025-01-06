package fi.fabianadrian.webhooklogger.common.webhook.serializer;

import net.kyori.adventure.text.Component;

public final class DiscordSerializer implements Serializer {
	@Override
	public String serialize(Component component) {
		return dev.vankka.mcdiscordreserializer.discord.DiscordSerializer.INSTANCE.serialize(component);
	}
}
