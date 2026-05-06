package fi.fabianadrian.webhooklogger.sponge;

import fi.fabianadrian.webhooklogger.common.DependencyManager;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginManager;

public final class SpongeDependencyManager extends DependencyManager {
	public SpongeDependencyManager(WebhookLogger webhookLogger) {
		super(webhookLogger);
	}

	@Override
	public void register() {
		PluginManager manager = Sponge.pluginManager();
		if (manager.plugin("miniplaceholders").isPresent()) {
			registerMiniPlaceholders();
		}
		if (manager.plugin("carbonchat").isPresent()) {
			registerCarbon();
		}
	}
}
