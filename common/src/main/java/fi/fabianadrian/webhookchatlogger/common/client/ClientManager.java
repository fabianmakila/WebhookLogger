package fi.fabianadrian.webhookchatlogger.common.client;

import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;

public class ClientManager {
	private final DiscordClient discordClient;

	public ClientManager(WebhookChatLogger wcl) {
		this.discordClient = new DiscordClient(wcl);
	}

	public void log(Message message) {
		this.discordClient.log(message);
	}

	public void reload() {
		this.discordClient.reload();
	}
}
