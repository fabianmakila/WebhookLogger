package fi.fabianadrian.webhooklogger.paper;

import dev.faststats.bukkit.BukkitMetrics;
import dev.faststats.core.ErrorTracker;
import dev.faststats.core.Metrics;
import fi.fabianadrian.webhooklogger.common.DependencyManager;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.paper.listener.*;
import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;
import java.util.List;

public final class WebhookLoggerPaper extends JavaPlugin implements Platform {
	public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();
	private final WebhookLogger webhookLogger;
	private final DependencyManager dependencyManager;
	private final Metrics metrics = BukkitMetrics.factory()
			.errorTracker(ERROR_TRACKER)
			.token("58c707ffefa0c399444b7a74b706dfe9")
			.create(this);
	private final PaperCommandManager commandManager;

	public WebhookLoggerPaper() {
		this.webhookLogger = new WebhookLogger(this);
		this.dependencyManager = new PaperDependencyManager(this);
		this.commandManager = new PaperCommandManager(this);
		registerListeners();
	}

	@Override
	public void onEnable() {
		this.metrics.ready();
		try {
			this.webhookLogger.startup();
		} catch (ConfigurateException e) {
			getSLF4JLogger().error("Couldn't load configuration", e);
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.commandManager.register();
	}

	@Override
	public void onDisable() {
		this.webhookLogger.shutdown();
		this.metrics.shutdown();
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
	public ComponentFlattener componentFlattener() {
		return PaperComponents.flattener();
	}

	@Override
	public DependencyManager dependencyManager() {
		return this.dependencyManager;
	}

	public WebhookLogger webhookLogger() {
		return this.webhookLogger;
	}

	private void registerListeners() {
		PluginManager pluginManager = getServer().getPluginManager();
		List.of(
				new ChatListener(webhookLogger),
				new CommandListener(webhookLogger),
				new DeathListener(webhookLogger),
				new JoinListener(webhookLogger),
				new QuitListener(webhookLogger)
		).forEach(listener -> pluginManager.registerEvents(listener, this));
	}
}
