package fi.fabianadrian.webhooklogger.common.client;

import fi.fabianadrian.webhooklogger.common.WebhookChatLogger;
import net.kyori.adventure.text.Component;

public final class ClientManager {
	private final DiscordClient discordClient;

	public ClientManager(WebhookChatLogger wcl) {
		this.discordClient = new DiscordClient(wcl);
	}

	public void send(Component component) {
		this.discordClient.send(component);
	}

	public void reload() {
		this.discordClient.reload();
	}
}
