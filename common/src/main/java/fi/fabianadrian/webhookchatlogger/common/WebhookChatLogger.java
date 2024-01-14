package fi.fabianadrian.webhookchatlogger.common;

import fi.fabianadrian.webhookchatlogger.common.client.ClientManager;
import fi.fabianadrian.webhookchatlogger.common.config.ConfigManager;
import fi.fabianadrian.webhookchatlogger.common.config.WebhookChatLoggerConfig;
import org.slf4j.Logger;

import java.nio.file.Path;

public class WebhookChatLogger {
	private final Logger logger;
	private final ConfigManager<WebhookChatLoggerConfig> configManager;
	private final ClientManager clientManager;

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
	}

	public void reload() {
		this.configManager.reload();
		this.clientManager.reload();
	}

	public void shutdown() {
		this.clientManager.shutdown();
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
}
