package fi.fabianadrian.webhooklogger.common;

import fi.fabianadrian.webhooklogger.common.client.ClientManager;
import fi.fabianadrian.webhooklogger.common.command.CaptionFormatter;
import fi.fabianadrian.webhooklogger.common.command.BaseCommand;
import fi.fabianadrian.webhooklogger.common.command.commands.ReloadCommand;
import fi.fabianadrian.webhooklogger.common.command.processor.WebhookLoggerCommandPreprocessor;
import fi.fabianadrian.webhooklogger.common.config.ConfigManager;
import fi.fabianadrian.webhooklogger.common.config.EventsConfig;
import fi.fabianadrian.webhooklogger.common.config.MainConfig;
import fi.fabianadrian.webhooklogger.common.dependency.DependencyManager;
import fi.fabianadrian.webhooklogger.common.locale.TranslationManager;
import fi.fabianadrian.webhooklogger.common.platform.Platform;
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
	private final ClientManager clientManager;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private final DependencyManager dependencyManager = new DependencyManager();

	public WebhookLogger(Platform platform) {
		this.platform = platform;

		new TranslationManager(platform.logger());

		this.configManager = new ConfigManager(platform.configPath(), platform.logger());
		this.configManager.reload();

		this.commandManager = platform.commandManager();
		setupCommandManager();
		registerCommands();

		this.clientManager = new ClientManager(this);
		this.clientManager.reload();
	}

	public boolean reload() {
		boolean success = this.configManager.reload();
		this.clientManager.reload();

		this.platform.registerListeners();

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
		return this.platform.logger();
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

	public CommandManager<Audience> commandManager() {
		return commandManager;
	}

	private void setupCommandManager() {
		this.commandManager.registerCommandPreProcessor(new WebhookLoggerCommandPreprocessor(this));
		this.commandManager.captionRegistry().registerProvider(TranslatableCaption.translatableCaptionProvider());
		MinecraftExceptionHandler.<Audience>createNative()
				.defaultHandlers()
				.captionFormatter(new CaptionFormatter())
				.registerTo(this.commandManager);
	}

	private void registerCommands() {
		List.of(
				new ReloadCommand(this)
		).forEach(BaseCommand::register);
	}
}
