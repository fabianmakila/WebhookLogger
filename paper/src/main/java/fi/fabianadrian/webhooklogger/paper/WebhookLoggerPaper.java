package fi.fabianadrian.webhooklogger.paper;

import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.paper.command.PaperWebhookLoggerCommand;
import fi.fabianadrian.webhooklogger.paper.listener.PaperListenerManager;
import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;

public final class WebhookLoggerPaper extends JavaPlugin implements Platform {
	private WebhookLogger webhookLogger;
	private PaperListenerManager listenerManager;

	@Override
	public void onEnable() {
		this.listenerManager = new PaperListenerManager(this);

		this.webhookLogger = new WebhookLogger(this);
		try {
			this.webhookLogger.startup();
		} catch (ConfigurateException e) {
			getSLF4JLogger().error("Couldn't load configuration", e);
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		PluginManager manager = getServer().getPluginManager();
		if (manager.isPluginEnabled("MiniPlaceholders")) {
			this.webhookLogger.dependencyManager().markAsPresent(Dependency.MINI_PLACEHOLDERS);
		}

		PaperWebhookLoggerCommand webhookLoggerCommand = new PaperWebhookLoggerCommand(this);
		webhookLoggerCommand.register();

		// bStats
		new Metrics(this, 18436);
	}

	@Override
	public void onDisable() {
		this.webhookLogger.shutdown();
	}

	@Override
	public Logger logger() {
		return getSLF4JLogger();
	}

	@Override
	public Path configPath() {
		return getDataPath();
	}

	@Override
	public ListenerManager listenerManager() {
		return this.listenerManager;
	}

	@Override
	public ComponentFlattener componentFlattener() {
		return PaperComponents.flattener();
	}

	public WebhookLogger webhookLogger() {
		return this.webhookLogger;
	}
}
