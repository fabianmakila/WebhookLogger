package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import fi.fabianadrian.webhooklogger.paper.listener.listeners.*;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public final class PaperListenerManager extends ListenerManager {
	public PaperListenerManager(WebhookLoggerPaper plugin) {
		super(plugin.webhookLogger());

		PluginManager pluginManager = plugin.getServer().getPluginManager();
		List.of(
				new ChatListener(super.webhookLogger),
				new CommandListener(super.webhookLogger),
				new DeathListener(super.webhookLogger),
				new JoinListener(super.webhookLogger),
				new QuitListener(super.webhookLogger)
		).forEach(listener -> {
			super.registry.put(listener.type(), listener);
			pluginManager.registerEvents(listener, plugin);
		});
	}
}
