package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.EventBuilder;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.common.listener.ListenerFactory;
import fi.fabianadrian.webhooklogger.paper.listener.listeners.ChatListener;
import fi.fabianadrian.webhooklogger.paper.listener.listeners.CommandListener;
import fi.fabianadrian.webhooklogger.paper.listener.listeners.DeathListener;
import fi.fabianadrian.webhooklogger.paper.listener.listeners.JoinQuitListener;

public final class PaperListenerFactory extends ListenerFactory {
	public PaperListenerFactory(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public AbstractListener<? extends EventBuilder> create(EventType type) {
		switch (type) {
			case CHAT -> {
				return new ChatListener(this.webhookLogger);
			}
			case COMMAND -> {
				return new CommandListener(this.webhookLogger);
			}
			case DEATH -> {
				return new DeathListener(this.webhookLogger);
			}
			case JOINQUIT -> {
				return new JoinQuitListener(this.webhookLogger);
			}
		}
		throw new IllegalStateException("Unknown EventType");
	}
}
