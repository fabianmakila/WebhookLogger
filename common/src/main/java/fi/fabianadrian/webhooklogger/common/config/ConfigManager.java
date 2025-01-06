package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.config.serializer.PatternSerializer;
import fi.fabianadrian.webhooklogger.common.config.serializer.ZoneIdSerializer;
import org.spongepowered.configurate.ConfigurateException;

import java.nio.file.Path;
import java.time.ZoneId;
import java.util.regex.Pattern;

public final class ConfigManager {
	private final ConfigLoader<MainConfig> mainConfigLoader;
	private final ConfigLoader<EventsConfig> eventsConfigLoader;
	private MainConfig mainConfig;
	private EventsConfig eventsConfig;

	public ConfigManager(Path configPath) {
		this.mainConfigLoader = new ConfigLoader<>(
				MainConfig.class,
				configPath.resolve("config.yml"),
				options -> options.header("WebhookLogger Main Configuration")
						.serializers(builder -> builder
								.register(Pattern.class, PatternSerializer.INSTANCE)
								.register(ZoneId.class, ZoneIdSerializer.INSTANCE)
						)
		);
		this.eventsConfigLoader = new ConfigLoader<>(
				EventsConfig.class,
				configPath.resolve("events.yml"),
				options -> options.header("WebhookLogger Events Configuration")
		);
	}

	public void reload() throws ConfigurateException {
		this.mainConfig = this.mainConfigLoader.load();
		this.mainConfigLoader.save(this.mainConfig);

		this.eventsConfig = this.eventsConfigLoader.load();
		this.eventsConfigLoader.save(this.eventsConfig);
	}

	public MainConfig mainConfig() {
		if (this.mainConfig == null) {
			throw new IllegalStateException("Main configuration isn't loaded");
		}
		return this.mainConfig;
	}

	public EventsConfig eventsConfig() {
		if (this.eventsConfig == null) {
			throw new IllegalStateException("Events configuration isn't loaded");
		}
		return this.eventsConfig;
	}
}
