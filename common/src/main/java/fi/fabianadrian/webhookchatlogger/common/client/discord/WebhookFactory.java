package fi.fabianadrian.webhookchatlogger.common.client.discord;

import fi.fabianadrian.webhookchatlogger.common.Message;
import fi.fabianadrian.webhookchatlogger.common.config.section.DiscordConfigSection;
import io.github._4drian3d.jdwebhooks.Embed;
import io.github._4drian3d.jdwebhooks.WebHook;

import java.util.List;
import java.util.StringJoiner;

public class WebhookFactory {
	private final DiscordConfigSection config;

	public WebhookFactory(DiscordConfigSection config) {
		this.config = config;
	}

	public WebHook constructWebhook(List<Message> messages) {

	}
}
