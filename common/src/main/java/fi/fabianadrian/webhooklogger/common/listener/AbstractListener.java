package fi.fabianadrian.webhooklogger.common.listener;

import fi.fabianadrian.webhooklogger.api.WebhookManager;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.PlaceholderFactory;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public abstract class AbstractListener {
	protected final WebhookLogger webhookLogger;
	protected final PlaceholderFactory placeholderFactory;
	private final WebhookManager webhookManager;

	public AbstractListener(WebhookLogger webhookLogger) {
		this.webhookLogger = webhookLogger;
		this.webhookManager = webhookLogger.webhookManager();
		this.placeholderFactory = new PlaceholderFactory(webhookLogger);
	}

	public abstract Key key();

	protected void post(String format, Audience audience, TagResolver.Builder builder) {
		TagResolver resolver = builder
				.resolver(this.placeholderFactory.timestamp())
				.build();

		Component component = WebhookLogger.MINI_MESSAGE.deserialize(format, audience, resolver);
		this.webhookManager.post(key(), component);
	}
}
