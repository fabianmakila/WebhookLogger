package fi.fabianadrian.webhooklogger.sponge;

import com.google.inject.Inject;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.command.Commander;
import fi.fabianadrian.webhooklogger.common.dependency.Dependency;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.sponge.listener.ChatListener;
import fi.fabianadrian.webhooklogger.sponge.listener.CommandListener;
import org.bstats.sponge.Metrics;
import org.incendo.cloud.CommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;
import java.util.List;

@Plugin("webhooklogger")
public final class WebhookLoggerSponge implements Platform {
	private WebhookLogger webhookLogger;
	private final PluginContainer container;
	private final Path configDir;
	private final Logger logger;
	private CommandManager<Commander> commandManager;

	@Inject
	public WebhookLoggerSponge(PluginContainer container, @ConfigDir(sharedRoot = false) Path configDir, Metrics.Factory metricsFactory) {
		this.container = container;
		this.configDir = configDir;
		this.logger = LoggerFactory.getLogger("webhooklogger");

		metricsFactory.make(23463);
	}

	@Listener
	public void onServerStart(final StartedEngineEvent<Server> event) {
		createCommandManager();
		this.webhookLogger = new WebhookLogger(this);

		if (Sponge.pluginManager().plugin("miniplaceholders").isPresent()) {
			this.webhookLogger.dependencyManager().markAsPresent(Dependency.MINI_PLACEHOLDERS);
		}

		registerListeners();
	}

	@Listener
	public void onServerStopping(final StoppingEngineEvent<Server> event) {
		this.webhookLogger.shutdown();
	}

	public WebhookLogger webhookLogger() {
		return this.webhookLogger;
	}

	private void registerListeners() {
		EventManager manager = Sponge.eventManager();
		List.of(
				new ChatListener(this),
				new CommandListener(this)
		).forEach(listener -> manager.registerListeners(this.container, listener));
	}

	@Override
	public Logger logger() {
		return this.logger;
	}

	@Override
	public Path configPath() {
		return this.configDir;
	}

	@Override
	public CommandManager<Commander> commandManager() {
		return this.commandManager;
	}

	private void createCommandManager() {

	}
}
