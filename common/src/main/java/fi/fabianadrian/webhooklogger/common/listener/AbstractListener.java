package fi.fabianadrian.webhooklogger.common.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.event.PlaceholderFactory;
import fi.fabianadrian.webhooklogger.common.webhook.WebhookClient;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListener {
	protected final WebhookLogger webhookLogger;
	protected final PlaceholderFactory placeholderFactory;
	protected final List<WebhookClient> webhooks = new ArrayList<>();

	public AbstractListener(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		this.placeholderFactory = new PlaceholderFactory(webhookLogger);
	}

	public void registerWebhook(WebhookClient webhook) {
		webhooks.add(webhook);
	}

	public void clearWebhooks() {
		this.webhooks.clear();
	}

	public abstract EventType type();

	protected void queue(String format, TagResolver.Builder builder) {
		TagResolver resolver = builder
				.resolver(this.placeholderFactory.timestamp())
				.build();

		Component component = MiniMessage.miniMessage().deserialize(format, resolver);
		this.webhooks.forEach(client -> client.add(component));
	}
}
