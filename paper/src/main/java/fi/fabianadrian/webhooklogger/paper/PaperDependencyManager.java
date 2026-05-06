package fi.fabianadrian.webhooklogger.paper;

import fi.fabianadrian.webhooklogger.common.DependencyManager;
import org.bukkit.plugin.PluginManager;

public final class PaperDependencyManager extends DependencyManager {
	private final WebhookLoggerPaper plugin;

	public PaperDependencyManager(WebhookLoggerPaper plugin) {
		super(plugin.webhookLogger());
		this.plugin = plugin;
	}

	@Override
	public void register() {
		PluginManager manager = this.plugin.getServer().getPluginManager();
		if (manager.isPluginEnabled("MiniPlaceholders")) {
			registerMiniPlaceholders();
		}
		if (manager.isPluginEnabled("CarbonChat")) {
			registerCarbon();
		}
	}
}
