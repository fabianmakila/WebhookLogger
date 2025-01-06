package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.sponge.listener.listeners.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.plugin.PluginContainer;

import java.util.List;

public final class SpongeListenerManager extends ListenerManager {
	public SpongeListenerManager(WebhookLogger webhookLogger, PluginContainer container) {
		super(webhookLogger);

		EventManager manager = Sponge.eventManager();
		List.of(
				new ChatListener(super.webhookLogger),
				new CommandListener(super.webhookLogger),
				new DeathListener(super.webhookLogger),
				new JoinListener(super.webhookLogger),
				new QuitListener(super.webhookLogger)
		).forEach(listener -> {
			super.registry.put(listener.type(), listener);
			manager.registerListeners(container, listener);
		});
	}
}
