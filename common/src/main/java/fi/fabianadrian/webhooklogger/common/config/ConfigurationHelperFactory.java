package fi.fabianadrian.webhooklogger.common.config;

import fi.fabianadrian.webhooklogger.common.config.serializer.ZoneIdSerializer;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import space.arim.dazzleconf.ConfigurationFactory;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.nio.file.Path;

public final class ConfigurationHelperFactory {
	private final Path configurationPath;
	private final SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder()
			.yamlSupplier(() -> {
				DumperOptions dumperOptions = new DumperOptions();
				// Enables comments
				dumperOptions.setProcessComments(true);
				dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
				return new Yaml(dumperOptions);
			})
			.commentMode(CommentMode.fullComments())
			.build();
	private final ConfigurationOptions options = new ConfigurationOptions.Builder()
			.addSerialiser(new ZoneIdSerializer())
			.sorter(new AnnotationBasedSorter())
			.build();

	public ConfigurationHelperFactory(Path configurationPath) {
		this.configurationPath = configurationPath;
	}

	public <C> ConfigurationHelper<C> create(String fileName, Class<C> configClass) {
		ConfigurationFactory<C> factory = SnakeYamlConfigurationFactory.create(
				configClass,
				this.options,
				this.yamlOptions
		);
		return new ConfigurationHelper<>(this.configurationPath, fileName, factory);
	}
}
