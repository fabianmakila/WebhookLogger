package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.event.EventType;
import org.spongepowered.api.Sponge;
import org.spongepowered.plugin.PluginContainer;

public final class ListenerFactory {
	private final PluginContainer container;
	private final WebhookLogger webhookLogger;

	public ListenerFactory(PluginContainer container, WebhookLogger webhookLogger) {
		this.container = container;
		this.webhookLogger = webhookLogger;
	}

	public void register(EventType type) {
		Object listener = create(type);
		Sponge.eventManager().registerListeners(this.container, listener);
	}

	private Object create(EventType type) {
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
