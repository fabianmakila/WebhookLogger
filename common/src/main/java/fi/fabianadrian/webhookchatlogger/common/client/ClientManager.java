package fi.fabianadrian.webhookchatlogger.common.client;

import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.WebhookChatLogger;
import fi.fabianadrian.webhookchatlogger.common.client.discord.DiscordClient;

public class ClientManager {
	private final WebhookChatLogger wcl;
	private DiscordClient discordClient;

	public ClientManager(WebhookChatLogger wcl) {
		this.wcl = wcl;

		this.discordClient = new DiscordClient(wcl);

		reload();
	}

	public void log(Message message) {
		this.discordClient.log(message);
	}

	public void reload() {
		this.discordClient.reload();
	}

	public void shutdown() {
		this.discordClient.shutdown();
	}
}
