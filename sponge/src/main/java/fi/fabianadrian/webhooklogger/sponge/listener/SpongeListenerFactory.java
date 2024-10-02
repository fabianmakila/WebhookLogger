package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import fi.fabianadrian.webhooklogger.common.listener.AbstractListener;
import fi.fabianadrian.webhooklogger.common.listener.ListenerFactory;
import fi.fabianadrian.webhooklogger.sponge.listener.listeners.ChatListener;
import fi.fabianadrian.webhooklogger.sponge.listener.listeners.CommandListener;
import fi.fabianadrian.webhooklogger.sponge.listener.listeners.DeathListener;
import fi.fabianadrian.webhooklogger.sponge.listener.listeners.JoinQuitListener;

public final class SpongeListenerFactory extends ListenerFactory {
	public SpongeListenerFactory(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	protected AbstractListener<?> create(EventType type) {
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
			case JOINQUIT -> {
				return new JoinQuitListener(webhookLogger);
			}
		}
		throw new IllegalStateException("Unknown EventType");
	}
}
