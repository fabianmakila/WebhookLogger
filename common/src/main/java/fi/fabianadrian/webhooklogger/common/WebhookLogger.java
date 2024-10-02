package fi.fabianadrian.webhooklogger.common;

import fi.fabianadrian.webhooklogger.common.command.BaseCommand;
import fi.fabianadrian.webhooklogger.common.command.CaptionFormatter;
import fi.fabianadrian.webhooklogger.common.command.commands.ReloadCommand;
import fi.fabianadrian.webhooklogger.common.command.processor.WebhookLoggerCommandPreprocessor;
import fi.fabianadrian.webhooklogger.common.config.ConfigManager;
import fi.fabianadrian.webhooklogger.common.config.EventsConfig;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import fi.fabianadrian.webhooklogger.common.dependency.DependencyManager;
import fi.fabianadrian.webhooklogger.common.listener.ListenerManager;
import fi.fabianadrian.webhooklogger.common.locale.TranslationManager;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
import fi.fabianadrian.webhooklogger.common.webhook.WebhookManager;
import net.kyori.adventure.audience.Audience;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.caption.TranslatableCaption;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class WebhookLogger {
	private final Platform platform;
	private final CommandManager<Audience> commandManager;
	private final ConfigManager configManager;
	private final WebhookManager webhookManager;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private final DependencyManager dependencyManager = new DependencyManager();

	public WebhookLogger(Platform platform) {
		this.platform = platform;

		new TranslationManager(platform.logger());

		configManager = new ConfigManager(platform.configPath(), platform.logger());

		commandManager = platform.commandManager();
		setupCommandManager();
		registerCommands();

		webhookManager = new WebhookManager(this);
	}

	public boolean reload() {
		boolean success = configManager.reload();
		platform.listenerManager().unregisterAll();

		webhookManager.reload();
		platform.listenerManager().registerAll();

		return success;
	}

	public void shutdown() {
		scheduler.shutdown();
	}

	public MainConfig mainConfig() {
		return configManager.mainConfig();
	}

	public EventsConfig eventsConfig() {
		return configManager.eventsConfig();
	}

	public Logger logger() {
		return platform.logger();
	}

	public ScheduledExecutorService scheduler() {
		return scheduler;
	}

	public DependencyManager dependencyManager() {
		return dependencyManager;
	}

	public CommandManager<Audience> commandManager() {
		return commandManager;
	}

	public ListenerManager listenerManager() {
		return platform.listenerManager();
	}

	private void setupCommandManager() {
		commandManager.registerCommandPreProcessor(new WebhookLoggerCommandPreprocessor(this));
		commandManager.captionRegistry().registerProvider(TranslatableCaption.translatableCaptionProvider());
		MinecraftExceptionHandler.createNative()
				.defaultHandlers()
				.captionFormatter(new CaptionFormatter())
				.registerTo(commandManager);
	}

	private void registerCommands() {
		List.of(
				new ReloadCommand(this)
		).forEach(BaseCommand::register);
	}
}
