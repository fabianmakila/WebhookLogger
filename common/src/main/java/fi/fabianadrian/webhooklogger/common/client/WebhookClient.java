package fi.fabianadrian.webhooklogger.common.client;

import net.kyori.adventure.text.Component;

public interface WebhookClient {
	void send(Component component);

	void reload();
}
