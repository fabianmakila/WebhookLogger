package fi.fabianadrian.webhooklogger.common.config;

import org.slf4j.Logger;
import space.arim.dazzleconf.helper.ConfigurationHelper;

import java.nio.file.Path;

public final class ConfigManager {
	private final Logger logger;
	private final ConfigurationHelper<MainConfig> mainConfigHelper;
	private final ConfigurationHelper<EventsConfig> eventsConfigHelper;

	private volatile MainConfig mainConfigData;
	private volatile EventsConfig eventsConfigData;

	public ConfigManager(Path configPath, Logger logger) {
		this.logger = logger;

		ConfigurationHelperFactory helperFactory = new ConfigurationHelperFactory(configPath);
		mainConfigHelper = helperFactory.create("config.yml", MainConfig.class);
		eventsConfigHelper = helperFactory.create("events.yml", EventsConfig.class);
	}

	public MainConfig mainConfig() {
		MainConfig configData = mainConfigData;
		if (configData == null) {
			throw new IllegalStateException("Configuration has not been loaded yet");
		}
		return configData;
	}

	public EventsConfig eventsConfig() {
		EventsConfig configData = eventsConfigData;
		if (configData == null) {
			throw new IllegalStateException("Configuration has not been loaded yet");
		}
		return configData;
	}

	public boolean reload() {
		boolean success = true;
		try {
			mainConfigData = mainConfigHelper.reloadConfigData();
		} catch (Exception e) {
			logger.error("Could not load config.yml, falling back to default configuration", e);
			mainConfigData = mainConfigHelper.getFactory().loadDefaults();
			success = false;
		}

		try {
			eventsConfigData = eventsConfigHelper.reloadConfigData();
		} catch (Exception e) {
			logger.error("Could not load events.yml, falling back to default configuration", e);
			eventsConfigData = eventsConfigHelper.getFactory().loadDefaults();
			success = false;
		}

		return success;
	}
}
