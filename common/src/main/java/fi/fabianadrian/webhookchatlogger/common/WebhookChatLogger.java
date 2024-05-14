package fi.fabianadrian.webhookchatlogger.common;

import fi.fabianadrian.webhookchatlogger.common.client.ClientManager;
import fi.fabianadrian.webhookchatlogger.common.config.EventsConfig;
import fi.fabianadrian.webhookchatlogger.common.config.ConfigManager;
import fi.fabianadrian.webhookchatlogger.common.config.MainConfig;
import fi.fabianadrian.webhookchatlogger.common.dependency.DependencyManager;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class WebhookChatLogger {
	private final Logger logger;
	private final ConfigManager<MainConfig> mainConfigManager;
	private final ConfigManager<EventsConfig> eventsConfigManager;
	private final ClientManager clientManager;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private final DependencyManager dependencyManager = new DependencyManager();

	public WebhookChatLogger(Logger logger, Path dataFolder) {
		this.logger = logger;
		this.mainConfigManager = ConfigManager.create(
				dataFolder,
				"config.yml",
				MainConfig.class,
				logger
		);
		this.mainConfigManager.reload();
		this.eventsConfigManager = ConfigManager.create(
				dataFolder,
				"events.yml",
				EventsConfig.class,
				logger
		);
		eventsConfigManager.reload();

		this.clientManager = new ClientManager(this);
		this.clientManager.reload();
	}

	public void reload() {
		this.mainConfigManager.reload();
		this.eventsConfigManager.reload();

		this.clientManager.reload();
	}

	public void shutdown() {
		this.scheduler.shutdown();
	}

	public MainConfig mainConfig() {
		return this.mainConfigManager.config();
	}

	public EventsConfig eventsConfig() {
		return this.eventsConfigManager.config();
	}

	public Logger logger() {
		return this.logger;
	}

	public ClientManager clientManager() {
		return this.clientManager;
	}

	public ScheduledExecutorService scheduler() {
		return this.scheduler;
	}

	public DependencyManager dependencyManager() {
		return this.dependencyManager;
	}
}
