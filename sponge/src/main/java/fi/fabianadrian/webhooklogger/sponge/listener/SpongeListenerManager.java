package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.sponge.WebhookLoggerSponge;
import fi.fabianadrian.webhooklogger.sponge.listener.listeners.*;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.plugin.PluginContainer;

import java.util.List;

public final class SpongeListenerManager extends ListenerManager {
	private final WebhookLoggerSponge plugin;
	private final PluginContainer container;

	public SpongeListenerManager(WebhookLoggerSponge plugin, PluginContainer container) {
		this.plugin = plugin;
		this.container = container;
	}

	@Override
	public void registerListeners() {
		WebhookLogger webhookLogger = this.plugin.webhookLogger();
		EventManager manager = Sponge.eventManager();
		List.of(
				new ChatListener(webhookLogger),
				new CommandListener(webhookLogger),
				new DeathListener(webhookLogger),
				new JoinListener(webhookLogger),
				new QuitListener(webhookLogger)
		).forEach(listener -> {
			super.registry.put(listener.type(), listener);
			manager.registerListeners(this.container, listener);
		});
	}
}
