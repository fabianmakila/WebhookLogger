package fi.fabianadrian.webhooklogger.common.config;

import org.slf4j.Logger;
import space.arim.dazzleconf.error.ConfigFormatSyntaxException;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.helper.ConfigurationHelper;

import java.io.IOException;
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
			this.mainConfigData = loadConfig(this.mainConfigHelper);
		} catch (Exception e) {
			this.logger.error("Could not load config.yml", e);
			success = false;
		}

		try {
			this.eventsConfigData = loadConfig(this.eventsConfigHelper);
		} catch (Exception e) {
			this.logger.error("Could not load events.yml", e);
			success = false;
		}

		return success;
	}

	private <C> C loadConfig(ConfigurationHelper<C> configHelper) throws ConfigLoadException {
		try {
			return configHelper.reloadConfigData();
		} catch (IOException e) {
			throw new ConfigLoadException(e);
		} catch (ConfigFormatSyntaxException e) {
			throw new ConfigLoadException("The YAML syntax in your configuration is invalid. Check your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/", e);
		} catch (InvalidConfigException e) {
			throw new ConfigLoadException("One of the values in your configuration is not valid. Check to make sure you have specified the right data types.", e);
		}
	}
}
