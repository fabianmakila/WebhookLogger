package fi.fabianadrian.webhookchatlogger.common.config;

import org.slf4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import space.arim.dazzleconf.ConfigurationFactory;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.error.ConfigFormatSyntaxException;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public final class ConfigManager<C> {
    private final Logger logger;
    private final ConfigurationHelper<C> configHelper;
    private volatile C configData;

    private ConfigManager(ConfigurationHelper<C> configHelper, Logger logger) {
        this.configHelper = configHelper;
        this.logger = logger;
    }

    public static <C> ConfigManager<C> create(Path configFolder, String fileName, Class<C> configClass, Logger logger) {
        // SnakeYaml example
        SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder()
            .yamlSupplier(() -> {
                DumperOptions dumperOptions = new DumperOptions();
                // Enables comments
                dumperOptions.setProcessComments(true);
                dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                return new Yaml(dumperOptions);
            })
            .commentMode(CommentMode.fullComments())
            .build();

        ConfigurationFactory<C> configFactory = SnakeYamlConfigurationFactory.create(
            configClass,
            ConfigurationOptions.defaults(),
            yamlOptions
        );
        return new ConfigManager<>(new ConfigurationHelper<>(configFolder, fileName, configFactory), logger);
    }

    public void reload() {
        try {
            configData = configHelper.reloadConfigData();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);

        } catch (ConfigFormatSyntaxException ex) {
            configData = configHelper.getFactory().loadDefaults();
            this.logger.error(
                "The yaml syntax in your configuration is invalid. " +
                    "Check your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/",
                ex
            );
        } catch (InvalidConfigException ex) {
            configData = configHelper.getFactory().loadDefaults();
            this.logger.error(
                "One of the values in your configuration is not valid. " +
                    "Check to make sure you have specified the right data types.",
                ex
            );
        }
    }

    public C config() {
        C configData = this.configData;
        if (configData == null) {
            throw new IllegalStateException("Configuration has not been loaded yet");
        }
        return configData;
    }

}
