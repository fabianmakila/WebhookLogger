package fi.fabianadrian.webhooklogger.paper.listener;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.paper.WebhookLoggerPaper;
import fi.fabianadrian.webhooklogger.paper.listener.listeners.*;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public final class PaperListenerManager extends ListenerManager {
	private final WebhookLoggerPaper plugin;

	public PaperListenerManager(WebhookLoggerPaper plugin) {
		this.plugin = plugin;
	}

	@Override
	public void registerListeners() {
		WebhookLogger webhookLogger = this.plugin.webhookLogger();
		PluginManager pluginManager = this.plugin.getServer().getPluginManager();
		List.of(
				new ChatListener(webhookLogger),
				new CommandListener(webhookLogger),
				new DeathListener(webhookLogger),
				new JoinListener(webhookLogger),
				new QuitListener(webhookLogger)
		).forEach(listener -> {
			super.registry.put(listener.type(), listener);
			pluginManager.registerEvents(listener, this.plugin);
		});
	}
}
