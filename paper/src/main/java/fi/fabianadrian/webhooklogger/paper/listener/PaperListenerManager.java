package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.listener.ListenerFactory;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public final class PaperListenerManager extends ListenerManager {
	private final WebhookLoggerPaper plugin;
	private final ListenerFactory factory;

	public PaperListenerManager(WebhookLoggerPaper plugin) {
		super(plugin.webhookLogger());
		this.plugin = plugin;
		factory = new PaperListenerFactory(plugin.webhookLogger());
	}

	@Override
	public void registerAll() {
		PluginManager manager = plugin.getServer().getPluginManager();
		registry.values().forEach(listener -> manager.registerEvents((Listener) listener, plugin));
	}

	@Override
	public void unregisterAll() {
		HandlerList.unregisterAll(plugin);
		registry.clear();
	}

	@Override
	protected ListenerFactory factory() {
		return factory;
	}
}
