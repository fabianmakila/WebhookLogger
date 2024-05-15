package fi.fabianadrian.webhooklogger.common.client;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import net.kyori.adventure.text.Component;

public final class ClientManager {
	private final DiscordClient discordClient;

	public ClientManager(WebhookLogger webhookLogger) {
		this.discordClient = new DiscordClient(webhookLogger);
	}

	public void send(Component component) {
		this.discordClient.send(component);
	}

	public void reload() {
		this.discordClient.reload();
	}
}
