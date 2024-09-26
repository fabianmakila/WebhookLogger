package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.listener.ListenerFactory;
import fi.fabianadrian.webhooklogger.common.listener.ListenerRegistry;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class PaperListenerRegistry extends ListenerRegistry {
	private final WebhookLoggerPaper plugin;
	private final ListenerFactory factory;

	public PaperListenerRegistry(WebhookLoggerPaper plugin) {
		super(plugin.webhookLogger());
		this.plugin = plugin;
		this.factory = new PaperListenerFactory(plugin.webhookLogger());
	}

	@Override
	public void registerAll() {
		PluginManager manager = this.plugin.getServer().getPluginManager();
		this.registry.values().forEach(listener -> manager.registerEvents((Listener) listener, this.plugin));
	}

	@Override
	public void unregisterAll() {
		HandlerList.unregisterAll(this.plugin);
		this.registry.clear();
	}

	@Override
	protected ListenerFactory factory() {
		return this.factory;
	}
}
