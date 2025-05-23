package fi.fabianadrian.webhooklogger.sponge;

import com.google.inject.Inject;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.sponge.listener.SpongeListenerManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bstats.sponge.Metrics;
import org.incendo.cloud.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.adventure.SpongeComponents;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;

@Plugin("webhooklogger")
public final class WebhookLoggerSponge implements Platform {
	private final PluginContainer container;
	private final Path configDir;
	private final Logger logger;
	private WebhookLogger webhookLogger;
	private CommandManager<Audience> commandManager;
	private SpongeListenerManager listenerManager;

	@Inject
	public WebhookLoggerSponge(PluginContainer container, @ConfigDir(sharedRoot = false) Path configDir, Metrics.Factory metricsFactory) {
		this.container = container;
		this.configDir = configDir;
		this.logger = LoggerFactory.getLogger("webhooklogger");

		metricsFactory.make(23463);
	}

	@Listener
	public void onServerStart(final StartedEngineEvent<Server> event) {
		this.listenerManager = new SpongeListenerManager(this, this.container);
		createCommandManager();

		this.webhookLogger = new WebhookLogger(this);
		try {
			this.webhookLogger.startup();
		} catch (ConfigurateException e) {
			this.logger.error("Couldn't load configuration", e);
			return;
		}

		if (Sponge.pluginManager().plugin("miniplaceholders").isPresent()) {
			webhookLogger.dependencyManager().markAsPresent(Dependency.MINI_PLACEHOLDERS);
		}
	}

	@Listener
	public void onServerStopping(final StoppingEngineEvent<Server> event) {
		webhookLogger.shutdown();
	}

	@Override
	public Logger logger() {
		return logger;
	}

	@Override
	public Path configPath() {
		return configDir;
	}

	@Override
	public CommandManager<Audience> commandManager() {
		return commandManager;
	}

	@Override
	public ListenerManager listenerManager() {
		return listenerManager;
	}

	@Override
	public ComponentFlattener componentFlattener() {
		return SpongeComponents.flattener();
	}

	public WebhookLogger webhookLogger() {
		return this.webhookLogger;
	}

	private void createCommandManager() {

	}
}
