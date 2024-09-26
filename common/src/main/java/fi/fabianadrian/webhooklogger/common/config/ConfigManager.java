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
		this.mainConfigHelper = helperFactory.create("config.yml", MainConfig.class);
		this.eventsConfigHelper = helperFactory.create("events.yml", EventsConfig.class);
	}

	public MainConfig mainConfig() {
		MainConfig configData = this.mainConfigData;
		if (configData == null) {
			throw new IllegalStateException("Configuration has not been loaded yet");
		}
		return configData;
	}

	public EventsConfig eventsConfig() {
		EventsConfig configData = this.eventsConfigData;
		if (configData == null) {
			throw new IllegalStateException("Configuration has not been loaded yet");
		}
		return configData;
	}

	public boolean reload() {
		boolean success = true;
		try {
			this.mainConfigData = this.mainConfigHelper.reloadConfigData();
		} catch (Exception e) {
			this.logger.error("Could not load config.yml, falling back to default configuration", e);
			this.mainConfigData = this.mainConfigHelper.getFactory().loadDefaults();
			success = false;
		}

		try {
			this.eventsConfigData = this.eventsConfigHelper.reloadConfigData();
		} catch (Exception e) {
			this.logger.error("Could not load events.yml, falling back to default configuration", e);
			this.eventsConfigData = this.eventsConfigHelper.getFactory().loadDefaults();
			success = false;
		}

		return success;
	}
}
