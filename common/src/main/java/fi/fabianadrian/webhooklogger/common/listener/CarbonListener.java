package fi.fabianadrian.webhooklogger.common.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.config.event.CarbonEventConfig;
import fi.fabianadrian.webhooklogger.common.event.CarbonPlaceholderFactory;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import net.draycia.carbon.api.CarbonChat;
import net.draycia.carbon.api.event.events.CarbonChatEvent;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public final class CarbonListener extends AbstractListener {
	private static final CarbonPlaceholderFactory CARBON_PLACEHOLDER_FACTORY = new CarbonPlaceholderFactory();

	public CarbonListener(WebhookLogger webhookLogger, CarbonChat carbonChat) {
		super(webhookLogger);

		carbonChat.eventHandler().subscribe(CarbonChatEvent.class, event -> {
			CarbonEventConfig config = webhookLogger.eventsConfig().carbon();

			if (!config.logCancelled() && event.cancelled()) {
				return;
			}


			TagResolver.Builder builder = TagResolver.builder().resolvers(
					CARBON_PLACEHOLDER_FACTORY.carbon(event)
			);
			queue(config.format(), event.sender(), builder);
		});
	}

	public void register() {
		super.webhookLogger.listenerManager().registerListener(this);
	}

	@Override
	public EventType type() {
		return EventType.CARBON;
	}
}
