package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.common.listener.ListenerFactory;
import fi.fabianadrian.webhooklogger.paper.listener.listeners.*;

public final class PaperListenerFactory extends ListenerFactory {
	public PaperListenerFactory(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public AbstractListener create(EventType type) {
		switch (type) {
			case CHAT -> {
				return new ChatListener(webhookLogger);
			}
			case COMMAND -> {
				return new CommandListener(webhookLogger);
			}
			case DEATH -> {
				return new DeathListener(webhookLogger);
			}
			case JOIN -> {
				return new JoinListener(webhookLogger);
			}
			case QUIT -> {
				return new QuitListener(webhookLogger);
			}
		}
		throw new IllegalStateException("Unknown EventType");
	}
}
