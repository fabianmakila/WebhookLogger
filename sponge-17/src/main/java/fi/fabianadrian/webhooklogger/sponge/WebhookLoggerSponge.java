package fi.fabianadrian.webhooklogger.sponge;

import com.google.inject.Inject;
import fi.fabianadrian.webhooklogger.common.DependencyManager;
import fi.fabianadrian.webhooklogger.common.WebhookLogger;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.sponge.listener.*;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.adventure.SpongeComponents;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.List;

@Plugin("webhooklogger")
public final class WebhookLoggerSponge implements Platform {
	private final Path configDir;
	private final Logger logger;
	private final DependencyManager dependencyManager;
	private final WebhookLogger webhookLogger;
	private final SpongeCommandManager commandManager;
	private final PluginContainer container;

	@Inject
	public WebhookLoggerSponge(PluginContainer container, @ConfigDir(sharedRoot = false) Path configDir) {
		this.configDir = configDir;
		this.logger = LoggerFactory.getLogger("webhooklogger");
		this.container = container;

		this.webhookLogger = new WebhookLogger(this);
		this.dependencyManager = new SpongeDependencyManager(this.webhookLogger);
		this.commandManager = new SpongeCommandManager(this.webhookLogger, container);
		registerListeners();
	}

	@Listener
	public void onServerStart(final StartedEngineEvent<Server> event) {
		Sponge.eventManager().registerListeners(this.container, this.commandManager, MethodHandles.lookup());

		try {
			this.webhookLogger.startup();
		} catch (ConfigurateException e) {
			this.logger.error("Couldn't load configuration", e);
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
	public ComponentFlattener componentFlattener() {
		return SpongeComponents.flattener();
	}

	@Override
	public DependencyManager dependencyManager() {
		return this.dependencyManager;
	}

	public WebhookLogger webhookLogger() {
		return this.webhookLogger;
	}

	private void registerListeners() {
		EventManager manager = Sponge.eventManager();
		List.of(
				new ChatListener(webhookLogger),
				new CommandListener(webhookLogger),
				new DeathListener(webhookLogger),
				new JoinListener(webhookLogger),
				new QuitListener(webhookLogger)
		).forEach(listener -> manager.registerListeners(this.container, listener, MethodHandles.lookup()));
	}
}
