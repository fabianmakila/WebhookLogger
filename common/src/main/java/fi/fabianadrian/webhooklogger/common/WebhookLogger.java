package fi.fabianadrian.webhooklogger.common;

import fi.fabianadrian.webhooklogger.common.client.ClientManager;
import fi.fabianadrian.webhooklogger.common.command.Commander;
import fi.fabianadrian.webhooklogger.common.command.WebhookLoggerCaptionFormatter;
import fi.fabianadrian.webhooklogger.common.command.WebhookLoggerCommand;
import fi.fabianadrian.webhooklogger.common.command.commands.ReloadCommand;
import fi.fabianadrian.webhooklogger.common.command.processor.WebhookLoggerCommandPreprocessor;
import fi.fabianadrian.webhooklogger.common.config.ConfigManager;
import fi.fabianadrian.webhooklogger.common.config.EventsConfig;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import fi.fabianadrian.webhooklogger.common.dependency.DependencyManager;
import fi.fabianadrian.webhooklogger.common.locale.TranslationManager;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.minecraft.extras.AudienceProvider;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.caption.TranslatableCaption;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class WebhookLogger {
	private final Logger logger;
	private final CommandManager<Commander> commandManager;
	private final ConfigManager configManager;
	private final ClientManager clientManager;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private final DependencyManager dependencyManager = new DependencyManager();

	public WebhookLogger(Platform platform) {
		this.logger = platform.logger();

		new TranslationManager(this.logger);

		this.configManager = new ConfigManager(platform.configPath(), this.logger);

		this.commandManager = platform.commandManager();
		setupCommandManager();
		registerCommands();

		this.clientManager = new ClientManager(this);

		reload();
	}

	public boolean reload() {
		boolean success = this.configManager.reload();
		this.clientManager.reload();

		return success;
	}

	public void shutdown() {
		this.scheduler.shutdown();
	}

	public MainConfig mainConfig() {
		return this.configManager.mainConfig();
	}

	public EventsConfig eventsConfig() {
		return this.configManager.eventsConfig();
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

	public CommandManager<Commander> commandManager() {
		return commandManager;
	}

	private void setupCommandManager() {
		this.commandManager.registerCommandPreProcessor(new WebhookLoggerCommandPreprocessor(this));
		this.commandManager.captionRegistry().registerProvider(TranslatableCaption.translatableCaptionProvider());
		AudienceProvider<Commander> audienceProvider = AudienceProvider.nativeAudience();
		MinecraftExceptionHandler.create(audienceProvider).defaultHandlers().captionFormatter(new WebhookLoggerCaptionFormatter()).registerTo(this.commandManager);
	}

	private void registerCommands() {
		List.of(
				new ReloadCommand(this)
		).forEach(WebhookLoggerCommand::register);
	}
}
