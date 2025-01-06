package fi.fabianadrian.webhooklogger.common.webhook.serializer;

import net.kyori.adventure.text.Component;

public interface Serializer {
	String serialize(Component component);
}
