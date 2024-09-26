package fi.fabianadrian.webhooklogger.sponge.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.listener.ListenerFactory;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.plugin.PluginContainer;

public class SpongeListenerManager extends ListenerManager {
	private final EventManager manager = Sponge.eventManager();
	private final PluginContainer container;
	private final SpongeListenerFactory factory;

	public SpongeListenerManager(WebhookLogger webhookLogger, PluginContainer container) {
		super(webhookLogger);
		this.container = container;
		this.factory = new SpongeListenerFactory(webhookLogger);
	}

	@Override
	public void registerAll() {
		this.registry.values().forEach(listener -> this.manager.registerListeners(this.container, listener));
	}

	@Override
	public void unregisterAll() {
		this.registry.values().forEach(this.manager::unregisterListeners);
	}

	@Override
	protected ListenerFactory factory() {
		return this.factory;
	}
}