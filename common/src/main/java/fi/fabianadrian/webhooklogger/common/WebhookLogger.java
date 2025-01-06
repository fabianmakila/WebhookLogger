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
import org.spongepowered.configurate.ConfigurateException;

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
		this.configManager = new ConfigManager(platform.configPath());
		this.webhookManager = new WebhookManager(this);

		this.commandManager = platform.commandManager();
		setupCommandManager();
		registerCommands();
	}

	public void startup() throws ConfigurateException {
		this.configManager.reload();
		this.platform.listenerManager().registerListeners();
		this.webhookManager.reload();
	}

	public void reload() throws ConfigurateException {
		this.configManager.reload();

		this.platform.listenerManager().clearRegisteredWebhooks();
		this.webhookManager.reload();
	}

	public void shutdown() {
		this.scheduler.shutdown();
		this.platform.listenerManager().clearRegisteredWebhooks();
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

	public ScheduledExecutorService scheduler() {
		return this.scheduler;
	}

	public DependencyManager dependencyManager() {
		return this.dependencyManager;
	}

	public CommandManager<Audience> commandManager() {
		return this.commandManager;
	}

	public ListenerManager listenerManager() {
		return this.platform.listenerManager();
	}

	private void setupCommandManager() {
		this.commandManager.registerCommandPreProcessor(new WebhookLoggerCommandPreprocessor(this));
		this.commandManager.captionRegistry().registerProvider(TranslatableCaption.translatableCaptionProvider());
		MinecraftExceptionHandler.createNative()
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
