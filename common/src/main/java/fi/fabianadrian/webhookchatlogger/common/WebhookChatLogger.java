package fi.fabianadrian.webhookchatlogger.common;

import fi.fabianadrian.webhookchatlogger.common.client.ClientManager;
import fi.fabianadrian.webhookchatlogger.common.config.ConfigManager;
import fi.fabianadrian.webhookchatlogger.common.config.WebhookChatLoggerConfig;
import fi.fabianadrian.webhookchatlogger.common.dependency.DependencyManager;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class WebhookChatLogger {
	private final Logger logger;
	private final ConfigManager<WebhookChatLoggerConfig> configManager;
	private final ClientManager clientManager;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private final DependencyManager dependencyManager = new DependencyManager();

	public WebhookChatLogger(Logger logger, Path dataFolder) {
		this.logger = logger;
		this.configManager = ConfigManager.create(
				dataFolder,
				"config.yml",
				WebhookChatLoggerConfig.class,
				logger
		);
		this.configManager.reload();

		this.clientManager = new ClientManager(this);
		this.clientManager.reload();
	}

	public void reload() {
		this.configManager.reload();
		this.clientManager.reload();
	}

	public void shutdown() {
		this.scheduler.shutdown();
	}

	public WebhookChatLoggerConfig config() {
		return this.configManager.config();
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
